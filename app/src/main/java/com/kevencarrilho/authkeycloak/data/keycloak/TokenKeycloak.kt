package com.kevencarrilho.authkeycloak.data.keycloak

import com.google.gson.annotations.SerializedName
import com.kevencarrilho.authkeycloak.data.model.Token

data class TokenKeycloak(
    @SerializedName("access_token") override var accessToken: String,
    @SerializedName("expires_in") override var expiresIn: Int,
    @SerializedName("refresh_expires_in") override var refreshExpiresIn: Int,
    @SerializedName("refresh_token") override var refreshToken: String,
    @SerializedName("token_type") override var tokenType: String,
    @SerializedName("not-before-policy") override var notBeforePolicy: Int,
    @SerializedName("session_state") override var sessionState: String,
    @SerializedName("scope") override var scope: String,
) : Token
