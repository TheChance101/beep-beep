package domain.gateway.local

import io.realm.kotlin.types.RealmList

interface ILocalConfigurationGateway {

    suspend fun saveAccessToken(token: String)
    suspend fun getAccessToken(): String
    suspend fun saveRefreshToken(token: String)
    suspend fun getRefreshToken(): String
    suspend fun saveKeepMeLoggedInFlag(isChecked: Boolean)
    suspend fun getKeepMeLoggedInFlag(): Boolean
    suspend fun clearTokens()
    suspend fun saveLanguageCode(code: String)
    suspend fun getLanguageCode(): String
    suspend fun savePriceLevel(priceLevel: String)
    suspend fun getPriceLevel(): String
    suspend fun saveIsFirstTimeUseApp(isFirstTimeUseApp: Boolean)
    suspend fun getIsFirstTimeUseApp(): Boolean
    suspend fun savePreferredFood(food: List<String>)
    suspend fun getPreferredFood(): List<String>
}