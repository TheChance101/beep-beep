package domain.gateway.local

interface ILocalConfigurationGateway {
    suspend fun saveAccessToken(token: String)
    suspend fun getAccessToken(): String
    suspend fun saveRefreshToken(token: String)
    suspend fun getRefreshToken(): String
    suspend fun saveKeepMeLoggedInFlag(isChecked: Boolean)
    suspend fun getKeepMeLoggedInFlag(): Boolean
    suspend fun clearTokens()
    suspend fun saveUserName(username: String)
    suspend fun getUsername(): String
    suspend fun saveTaxiId(taxiId: String)
    suspend fun getTaxiId(): String
}