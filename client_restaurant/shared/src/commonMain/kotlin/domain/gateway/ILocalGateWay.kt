package domain.gateway

/**
 * Created by Aziza Helmy on 8/24/2023.
 */

interface ILocalGateWay {

    suspend fun saveAccessToken(token: String)
    suspend fun getAccessToken(): String
    suspend fun saveRefreshToken(token: String)
    suspend fun getRefreshToken(): String
    suspend fun saveLoggedInFlag(isChecked: Boolean)
    suspend fun getLoggedInFlag(): Boolean

}