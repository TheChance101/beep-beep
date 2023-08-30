package domain.gateway.remote


interface IIdentityRemoteGateway {

    suspend fun loginUser(userName: String, password: String): Pair<String, String>

    suspend fun refreshAccessToken(refreshToken: String): Pair<String, String>

}