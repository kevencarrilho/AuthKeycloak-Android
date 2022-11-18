package com.kevencarrilho.authkeycloak.data.model

interface Token{
    var accessToken: String
    var expiresIn: Int
    var refreshExpiresIn: Int
    var refreshToken: String
    var tokenType: String
    var notBeforePolicy: Int
    var sessionState: String
    var scope: String
}