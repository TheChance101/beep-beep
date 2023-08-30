package domain.gateway.remote


interface IRemoteIdentityGateway {

    suspend fun loginUser(userName: String, password: String): Pair<String, String>

    suspend fun refreshAccessToken(refreshToken: String): Pair<String, String>

}