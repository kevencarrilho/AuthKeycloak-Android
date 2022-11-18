package com.kevencarrilho.authkeycloak.data.login.keycloak

import com.google.gson.JsonObject
import com.kevencarrilho.authkeycloak.data.keycloak.TokenKeycloak
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*


interface KeycloakRequest {

    @POST(value = "auth/realms/{REALM_NAME}/protocol/openid-connect/token")
    @FormUrlEncoded
    fun getToken(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("client_id") clientId: String = CLIENTE_ID,
        @Field("client_secret") clientSecret: String = CLIENT_SECRET,
        @Field("grant_type") grantType: String = GRANT_TYPE,
        @Path("REALM_NAME") realmName: String = REALM_NAME,
    ): Call<TokenKeycloak>

    @POST(value = "auth/realms/{REALM_NAME}/protocol/openid-connect/token")
    @FormUrlEncoded
    fun updateToken(
        @Field("refresh_token") refreshToken: String,
        @Field("client_id") clientId: String = CLIENTE_ID,
        @Field("client_secret") clientSecret: String = CLIENT_SECRET,
        @Field("grant_type") grantType: String = GRANT_TYPE,
        @Path("REALM_NAME") realmName: String = REALM_NAME,
    ): Call<TokenKeycloak>

    @POST(value = "/auth/realms/{REALM_NAME}/protocol/openid-connect/logout")
    @FormUrlEncoded
    fun logout(
        @Field("refresh_token") refreshToken: String,
        @Field("client_id") clientId: String = CLIENTE_ID,
        @Field("client_secret") clientSecret: String = CLIENT_SECRET,
        @Path("REALM_NAME") realmName: String = REALM_NAME,
    ): Call<JsonObject>

    @PUT(value = "/auth/realms/{REALM_NAME}/account/")
    fun updateUser(
        @Header("Authorization") token: String,
        @Body userKeycloak: JSONObject,
        @Path("REALM_NAME") realmName: String = REALM_NAME,
    ): Call<JsonObject>

}