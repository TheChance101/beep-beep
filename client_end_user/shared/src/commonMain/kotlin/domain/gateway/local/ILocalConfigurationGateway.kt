package domain.gateway.local

import kotlinx.coroutines.flow.Flow

interface ILocalConfigurationGateway {
    suspend fun saveAccessToken(token: String)
    suspend fun getAccessToken(): String
    suspend fun getAccessTokenStream(): Flow<String>
    suspend fun saveRefreshToken(token: String)
    suspend fun getRefreshToken(): String
    suspend fun saveKeepMeLoggedInFlag(isChecked: Boolean)
    suspend fun getKeepMeLoggedInFlag(): Boolean
    suspend fun clearTokens()
    suspend fun saveLanguageCode(code: String)
    suspend fun getLanguageCodeFlow(): Flow<String>
    suspend fun getLanguageCode(): String
    suspend fun savePriceLevel(priceLevel: String)
    suspend fun getPriceLevel(): String
    suspend fun savePreferredRideQuality(rideQuality: String)
    suspend fun getPreferredRideQuality(): String
    suspend fun saveIsFirstTimeUseApp(isFirstTimeUseApp: Boolean)
    suspend fun getIsFirstTimeUseApp(): Boolean
    suspend fun savePreferredFood(food: List<String>)
    suspend fun getPreferredFood(): List<String>

    suspend fun saveCartStatus(isCartEmpty: Boolean)
    suspend fun getCartStatus(): Flow<Boolean>

    suspend fun removeAccessToken()
    suspend fun removeRefreshToken()
}
