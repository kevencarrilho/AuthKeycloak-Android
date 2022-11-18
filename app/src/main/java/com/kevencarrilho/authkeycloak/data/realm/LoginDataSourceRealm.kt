package com.kevencarrilho.authkeycloak.data.realm

import android.content.res.Resources.NotFoundException
import com.kevencarrilho.authkeycloak.data.login.Result
import com.kevencarrilho.authkeycloak.data.model.LoginDataSourceModel
import com.kevencarrilho.authkeycloak.data.model.Providers
import com.kevencarrilho.authkeycloak.data.model.Token
import com.kevencarrilho.authkeycloak.data.model.User
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class LoginDataSourceRealm: LoginDataSourceModel {
    override val provider: Providers
        get() = Providers.REALM
    override var password: String = ""
    override var token: Result<Token> = Result.Error(NullPointerException())
    override var user: User = UserRealm()
    private lateinit var dataBase: Realm
    fun insertDependencies(database: Realm): LoginDataSourceModel {
        dataBase = database
        return this
    }

    override fun login(): Result<Pair<User, Token>> {
        val result = dataBase.query<UserRealm>().first().find()
        return if (result != null) {
            Result.Success(Pair(result as User, result.token as Token))
        } else {
            Result.Error(NotFoundException())
        }
    }

    override fun logout(): Boolean {
        dataBase.writeBlocking {
            deleteAll()
        }
        return true
    }

    override fun saveUser(): String {
        dataBase.writeBlocking {
            if(token is Result.Success){
                val tokenRealm = copyToRealm((token as Result.Success).data as TokenRealm)
                copyToRealm(
                    UserRealm().apply {
                        this@apply.userId = user.userId
                        this@apply.username = user.username
                        this@apply.email= user.email
                        this@apply.givenName = user.givenName
                        this@apply.familyName = user.familyName
                        this@apply.token = tokenRealm
                        this@apply.password = this@LoginDataSourceRealm.password
                    }
                )
            }
        }
        return ""
    }

}