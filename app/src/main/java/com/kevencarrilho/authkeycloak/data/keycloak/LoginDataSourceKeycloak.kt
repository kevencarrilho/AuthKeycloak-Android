package com.kevencarrilho.authkeycloak.data.login.keycloak

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kevencarrilho.authkeycloak.data.keycloak.TokenKeycloak
import com.kevencarrilho.authkeycloak.data.keycloak.UserKeycloak
import com.kevencarrilho.authkeycloak.data.model.LoginDataSourceModel
import com.kevencarrilho.authkeycloak.data.model.Providers
import com.kevencarrilho.authkeycloak.data.model.Token
import com.kevencarrilho.authkeycloak.data.model.User
import com.kevencarrilho.authkeycloak.data.login.Result
import io.fusionauth.jwt.domain.JWT
import io.fusionauth.jwt.rsa.RSAVerifier
import org.json.JSONObject
import java.io.IOException


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

class LoginDataSourceKeycloak: LoginDataSourceModel {
    override val provider = Providers.KEYCLOAK
    override var password: String = ""
    private lateinit var request: KeycloakRequest
    override var token: Result<Token> = Result.Error(NullPointerException())
    override var user: User = UserKeycloak("","","","","")

    fun insertDependencies(keycloakRequest: KeycloakRequest): LoginDataSourceModel {
        request = keycloakRequest
        return this
    }

    override fun login(): Result<Pair<UserKeycloak, Token>> {
        token = requestToken()
        if (token is Result.Success) {
            val user = readUserSaved()
            if (user is Result.Success)
                return Result.Success(
                    Pair(user.data as UserKeycloak, (token as Result.Success).data)
                )
            else return Result.Error(IOException(""))
        } else return Result.Error((token as Result.Error).exception)
    }

    fun login(oldToken: Token): Result<Pair<User, Token>> {
        try{
            token = updateToken(oldToken.refreshToken) as Result.Success
            val user = readUserSaved()
            if (user is Result.Success)
                return Result.Success(
                    Pair(user.data as UserKeycloak, (token as Result.Success).data)
                )
            else return Result.Error(IOException(""))
        } catch (e: Exception) {
            return Result.Error(IOException("Error logging in", null))
        }
    }

    override fun logout(): Boolean =
        request.logout((token as Result.Success).data.refreshToken).execute().isSuccessful


    override fun saveUser(): String =
        if (token is Result.Success) {
            val data = (token as Result.Success).data

            request.updateUser(
                token = "${data.tokenType} ${data.accessToken}",
                userKeycloak = JSONObject((user as UserKeycloak).toJson())
            ).execute().message()
        }
    else "Invalid token"

    @SuppressWarnings
    fun readUserSaved(): Result<User> =
            when(token is Result.Success){
                true ->
                Result.Success(
                    extractyoDataDecoded(
                        decode(
                            (token as Result.Success<Token>
                                    ).data.accessToken)
                    )
                )
                else -> Result.Error(java.lang.Exception("Not read user"))
            }


    private fun requestToken(): Result<TokenKeycloak> {
        val responseToken = request.getToken(username = user.username, password = password).execute()
        if (responseToken.isSuccessful){
            val result: Token = responseToken.body()!!
            return Result.Success(result as TokenKeycloak)
        } else {
            return Result.Error(IOException(responseToken.message()))
        }
     }


    private fun updateToken(refreshToken: String): Result<TokenKeycloak> {
        val responseToken = request.updateToken(refreshToken).execute()
        if (responseToken.isSuccessful){
            val result: Token = responseToken.body()!!
            return Result.Success(result as TokenKeycloak)
        } else {
            return Result.Error(IOException(responseToken.message()))
        }
     }

    private fun decode(encodedJWT: String): JWT = JWT.getDecoder()
        .decode(encodedJWT, RSAVerifier.newVerifier(PEM_RS256))

    private fun extractyoDataDecoded(data: JWT): User = UserKeycloak(
            userId = data.getString("sub"),
            username = data.getString("preferred_username"),
            email = data.getString("email"),
            givenName = data.getString("given_name"),
            familyName = data.getString("family_name"),
        )
}

