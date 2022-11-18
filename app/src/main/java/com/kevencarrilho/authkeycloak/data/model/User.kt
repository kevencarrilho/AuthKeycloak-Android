package com.kevencarrilho.authkeycloak.data.model

interface User {
    var userId: String
    var username: String
    var email: String
    var givenName: String
    var familyName: String
}