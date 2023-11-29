package domain.gateway.remote

import domain.entity.Session


interface IIdentityRemoteGateway {
    suspend fun getDeviceToken(): String

    suspend fun loginUser(userName: String, password: String): Session

    suspend fun refreshAccessToken(refreshToken: String): Pair<String, String>

    suspend fun createRequestPermission(
        restaurantRequestPermission: String,
        ownerEmail: String,
        description: String,
    ): Boolean
}
