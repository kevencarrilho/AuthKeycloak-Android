package com.kevencarrilho.authkeycloak.data.realm

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class Instance2Realm {
    companion object{
        fun getInstance(): Realm = Realm.open(
            RealmConfiguration
                .Builder(schema = setOf(TokenRealm::class, UserRealm::class))
                .build()
        )
    }
}