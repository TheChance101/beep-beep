package data.gateway.local

import data.local.model.UserConfigurationCollection
import domain.gateway.local.ILocalConfigurationGateway
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalConfigurationGateway(private val realm: Realm) : ILocalConfigurationGateway {

    init {
        createUserConfiguration()
    }

    private fun createUserConfiguration() {
        realm.writeBlocking {
            copyToRealm(UserConfigurationCollection().apply {
                id = CONFIGURATION_ID
            })
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

    override suspend fun saveLanguageCode(code: String) {
        return realm.write {
            query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
                .find()?.languageCode = code
        }
    }

    override suspend fun getLanguageCodeFlow(): Flow<String> {
        return realm.query<UserConfigurationCollection>(
            "$ID == $CONFIGURATION_ID"
        ).asFlow().map { result ->
            result.list.find { it.languageCode.isNotEmpty() }?.languageCode ?: "en"
        }
    }

    override suspend fun getLanguageCode(): String {
        return realm.query<UserConfigurationCollection>(
            "$ID == $CONFIGURATION_ID"
        ).first().find()?.languageCode ?: "en"
    }

    override suspend fun getAccessToken(): String {
        return realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
            .find()?.accessToken ?: ""
    }

    override suspend fun getAccessTokenStream(): Flow<String> {
        return realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID")
            .asFlow()
            .map { result ->
                result.list.find { it.accessToken.isNotEmpty() }?.accessToken ?: ""
            }
    }

    override suspend fun getRefreshToken(): String {
        return realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
            .find()?.refreshToken ?: ""
    }

    override suspend fun clearTokens() {
        realm.write { delete(query<UserConfigurationCollection>()) }
    }

    override suspend fun savePreferredFood(food: List<String>) {
        return realm.write {
            query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
                .find()?.preferredFood?.addAll(food.toRealmList())
        }
    }

    override suspend fun getPreferredFood(): List<String> {
        return realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
            .find()?.preferredFood?.toList() ?: emptyList()
    }

    override suspend fun saveCartStatus(isCartEmpty: Boolean) {
        realm.write {
            query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
                .find()?.isCartEmpty = isCartEmpty
        }
    }

    override suspend fun getCartStatus(): Flow<Boolean> {
        return realm.query<UserConfigurationCollection>(
            "$ID == $CONFIGURATION_ID"
        ).asFlow().map { result ->
            result.list.find { !it.isCartEmpty }?.isCartEmpty ?: true
        }
    }

    override suspend fun removeAccessToken() {
        realm.write {
            query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
                .find()?.accessToken = ""
        }
    }

    override suspend fun removeRefreshToken() {
        realm.write {
            query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
                .find()?.refreshToken = ""
        }
    }

    override suspend fun savePriceLevel(priceLevel: String) {
        realm.write {
            query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
                .find()?.priceLevel = priceLevel
        }
    }

    override suspend fun getPriceLevel(): String {
        return realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
            .find()?.priceLevel ?: "$"
    }

    override suspend fun savePreferredRideQuality(rideQuality: String) {
        realm.write {
            query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
                .find()?.rideQuality = rideQuality
        }
    }

    override suspend fun getPreferredRideQuality(): String {
        return realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
            .find()?.rideQuality ?: "low"
    }

    override suspend fun saveIsFirstTimeUseApp(isFirstTimeUseApp: Boolean) {
        realm.write {
            query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
                .find()?.isFirstTimeUseApp = isFirstTimeUseApp
        }
    }

    override suspend fun getIsFirstTimeUseApp(): Boolean {
        return realm.query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first()
            .find()?.isFirstTimeUseApp ?: true
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
