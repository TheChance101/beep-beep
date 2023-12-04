package data.gateway.local

import data.local.model.UserConfigurationCollection
import domain.entity.AddressInfo
import domain.entity.Location
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
    override suspend fun saveRestaurantLocation(addressInfo: AddressInfo) {
        realm.write { query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
                .find()?.apply {
                    latitude = addressInfo.location.latitude
                    longitude = addressInfo.location.longitude
                    address = addressInfo.address
                }
        }
    }

    override suspend fun getRestaurantId(): String {
        return realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
            .find()?.restaurantId ?: ""
    }
    override suspend fun getRestaurantLocation(): AddressInfo {
        println("getting location in local")
        val latitude= realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first().find()?.latitude ?:0.0
        val longitude= realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first().find()?.longitude?:0.0
        val address= realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first().find()?.address?:""
        return AddressInfo(Location(latitude,longitude),address)
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