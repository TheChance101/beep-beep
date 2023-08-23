package domain.gateway

interface IRestaurantGateWay {

    // region login

    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String

    // endregion

}