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

    override suspend fun saveRefreshToken(token: String) {
        realm.write {
            query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
                .find()?.refreshToken = token
        }
    }

    override suspend fun getAccessToken(): String {
        return realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
            .find()?.accessToken ?: ""
    }

    override suspend fun getRefreshToken(): String {
        return realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
            .find()?.refreshToken ?: ""
    }

    override suspend fun clearTokens() {
        realm.write { delete(query<UserConfigurationCollection>()) }
    }

    override suspend fun saveRestaurantId(restaurantId: String) {
        realm.write {
            query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
                .find()?.restaurantId = restaurantId
        }
    }

    override suspend fun getRestaurantId(): String {
        return realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
            .find()?.restaurantId ?: ""
    }

    override suspend fun saveNumberOfRestaurants(numberOfRestaurants: Int) {
        realm.write {
            query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
                .find()?.numberOfRestaurants = numberOfRestaurants
        }
    }

    override suspend fun getNumberOfRestaurants(): Int {
        return realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
            .find()?.numberOfRestaurants ?: 0
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

    companion object {
        private const val CONFIGURATION_ID = 0
        private const val ID = "id"
    }


}