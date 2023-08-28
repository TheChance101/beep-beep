package data.gateway.local

import data.local.model.UserConfigurationCollection
import domain.gateway.local.ILocalConfigurationGateway
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class LocalConfigurationGateway(private val realm: Realm) : ILocalConfigurationGateway {

    override suspend fun saveAccessToken(token: String) {
        realm.write {
            copyToRealm(UserConfigurationCollection().apply {
                this.accessToken = token
            })
        }
    }

    override suspend fun getAccessToken(): String {
        return realm.query<UserConfigurationCollection>().first().find()?.accessToken
            ?: throw Exception("Access Token not found")
    }

    override suspend fun saveRefreshToken(token: String) {
        realm.write {
            copyToRealm(UserConfigurationCollection().apply {
                this.refreshToken = token
            })
        }
    }
    override suspend fun getRefreshToken(): String {
        return realm.query<UserConfigurationCollection>().first().find()?.refreshToken
            ?: throw Exception("Refresh Token not found")
    }

    override suspend fun saveKeepMeLoggedInFlag(isChecked: Boolean) {
        realm.write {
            copyToRealm(UserConfigurationCollection().apply {
                this.isKeepMeLoggedInMeChecked = isChecked
            })
        }
    }

    override suspend fun getKeepMeLoggedInFlag(): Boolean {
        return realm.query<UserConfigurationCollection>().first().find()?.isKeepMeLoggedInMeChecked
            ?: throw Exception("Nothing ..")
    }

    override suspend fun clearTokens() {
        realm.write { delete(query<UserConfigurationCollection>()) }
    }

}