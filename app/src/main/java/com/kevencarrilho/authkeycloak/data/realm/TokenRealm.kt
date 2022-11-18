package com.kevencarrilho.authkeycloak.data.realm

import com.kevencarrilho.authkeycloak.data.model.Token
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class TokenRealm: Token, RealmObject {
    override var accessToken: String = ""
    override var expiresIn: Int = 0
    override var refreshExpiresIn: Int = 0
    override var refreshToken: String = ""
    override var tokenType: String = ""
    override var notBeforePolicy: Int = 0
    @PrimaryKey override var sessionState: String = ""
    override var scope: String = ""
}