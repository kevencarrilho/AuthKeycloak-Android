package com.kevencarrilho.authkeycloak.data.keycloak

import com.kevencarrilho.authkeycloak.data.model.User

data class UserKeycloak(
    override var userId: String,
    override var username: String = "",
    override var email: String = "",
    override var givenName: String = "",
    override var familyName: String = ""
) : User {
    var enable: Boolean = true
    val displayName: String = "${givenName} ${familyName}"

    fun toJson(): String = """{
        "email": "${email}",
        "emailVerified": true,
        "enabled": ${enable},
        "firstName": "${givenName}",
        "lastName": "${familyName}",
        "requiredActions": [],
        "username": "${username}",
    }""".trimIndent()
}