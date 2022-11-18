package com.kevencarrilho.authkeycloak.data.login.keycloak

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Instance2Keycloak {
    companion object{
        fun getInstance(urlBase: String): KeycloakRequest = Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(KeycloakRequest::class.java)
    }
}