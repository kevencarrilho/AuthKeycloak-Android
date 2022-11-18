package com.kevencarrilho.authkeycloak.data.realm

import com.kevencarrilho.authkeycloak.data.model.User
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class UserRealm(): User, RealmObject {
    @PrimaryKey override var userId: String = ""
    override var username: String = ""
    override var email: String = ""
    override var givenName: String = ""
    override var familyName: String = ""

    var token: TokenRealm? = null
    var password: String = ""
}
