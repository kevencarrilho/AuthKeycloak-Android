package com.kevencarrilho.authkeycloak.data.model

import com.kevencarrilho.authkeycloak.data.login.Result


interface LoginDataSourceModel {
    val provider: Providers
    var password: String
    var token: Result<Token>
    var user: User

    fun login(): Result<Pair<User, Token>>
    fun logout(): Boolean
    fun saveUser(): String
}