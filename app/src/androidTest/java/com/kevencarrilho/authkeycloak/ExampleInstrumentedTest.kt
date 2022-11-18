package com.kevencarrilho.authkeycloak

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kevencarrilho.authkeycloak.data.keycloak.UserKeycloak
import com.kevencarrilho.authkeycloak.data.login.Result
import com.kevencarrilho.authkeycloak.data.login.keycloak.Instance2Keycloak
import com.kevencarrilho.authkeycloak.data.login.keycloak.LoginDataSourceKeycloak
import com.kevencarrilho.authkeycloak.data.model.Token
import com.kevencarrilho.authkeycloak.data.model.User
import com.kevencarrilho.authkeycloak.data.realm.Instance2Realm
import com.kevencarrilho.authkeycloak.data.realm.LoginDataSourceRealm

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.kevencarrilho.authkeycloak", appContext.packageName)


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

            loginDataSourceRealm.saveUser()




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