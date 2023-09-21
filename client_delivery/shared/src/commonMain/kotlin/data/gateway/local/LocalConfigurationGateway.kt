package data.gateway.local

import data.local.model.UserConfigurationCollection
import domain.gateway.local.ILocalConfigurationGateway
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class LocalConfigurationGateway(private val realm: Realm) : ILocalConfigurationGateway {

    init {
        createUserConfiguration()
    }

    private fun createUserConfiguration() {
        val hasValue =
            realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first().find()

        if (hasValue == null) {
            realm.writeBlocking {
                copyToRealm(UserConfigurationCollection().apply {
                    id = CONFIGURATION_ID
                })
            }
        }
    }

    override suspend fun saveAccessToken(token: String) {
        realm.write {
            query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
                .find()?.accessToken = token
        }
    }

    override suspend fun getAccessToken(): String {
        return realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
            .find()?.accessToken ?: ""
    }

    override suspend fun saveRefreshToken(token: String) {
        realm.write {
            query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
                .find()?.refreshToken = token
        }
    }

    override suspend fun getRefreshToken(): String {
        return realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
            .find()?.refreshToken ?: ""
    }

    override suspend fun saveKeepMeLoggedInFlag(isChecked: Boolean) {
        realm.write {
            query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
                .find()?.isKeepMeLoggedInMeChecked = isChecked
        }
    }

    override suspend fun getKeepMeLoggedInFlag(): Boolean {
        return realm.query<UserConfigurationCollection>(
            "$ID == $CONFIGURATION_ID"
        ).first().find()?.isKeepMeLoggedInMeChecked ?: false
    }

    override suspend fun clearTokens() {
        realm.write { delete(query<UserConfigurationCollection>()) }
    }


    companion object {
        private const val CONFIGURATION_ID = 0
        private const val ID = "id"
    }
}