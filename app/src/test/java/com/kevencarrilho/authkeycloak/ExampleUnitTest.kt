package com.kevencarrilho.authkeycloak

import com.kevencarrilho.authkeycloak.data.keycloak.UserKeycloak
import com.kevencarrilho.authkeycloak.data.login.keycloak.Instance2Keycloak
import com.kevencarrilho.authkeycloak.data.login.keycloak.LoginDataSourceKeycloak
import com.kevencarrilho.authkeycloak.data.login.Result
import com.kevencarrilho.authkeycloak.data.model.Token
import com.kevencarrilho.authkeycloak.data.model.User
import com.kevencarrilho.authkeycloak.data.realm.Instance2Realm
import com.kevencarrilho.authkeycloak.data.realm.LoginDataSourceRealm
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
//    @Test
//    fun addition_isCorrect() {
//
//    }

    @Test
    fun teste_KeycoakLogin(){
        val url = "http://192.168.1.9:8080/"
        val loginDataSource = LoginDataSourceKeycloak().insertDependencies(
            Instance2Keycloak.getInstance(url)) as LoginDataSourceKeycloak

        val loginDataSourceRealm = LoginDataSourceRealm().insertDependencies(Instance2Realm.getInstance())

        loginDataSource.user = UserKeycloak("","keven","","","")
        loginDataSource.password = "123456"
        val result = loginDataSource.login()
        var user: User
        var token: Token
        if(result is Result.Success){
            user = result.data.first
            token = result.data.second
            loginDataSourceRealm.user = user
            loginDataSourceRealm.token = Result.Success(token)





/*            loginDataSource.user = (loginDataSource.readUserSaved() as Result.Success).data
            user.familyName = "Pinto"
            println("User ID: ${user.userId}")
            println("Atualização do usuário: ${loginDataSource.saveUser()}")
            val resultToken = loginDataSource.login(token)
            if(resultToken is Result.Success){
                token = resultToken.data.second
            }

            println("Logout ${loginDataSource.logout()}")*/


        }
    }
}