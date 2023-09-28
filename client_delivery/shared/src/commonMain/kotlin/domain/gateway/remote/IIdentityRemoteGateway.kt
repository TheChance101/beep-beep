package domain.gateway.remote

import domain.entity.DeliveryRequestPermission
import domain.entity.Session

interface IIdentityRemoteGateway {
    //region login
    suspend fun loginUser(userName: String, password: String): Session

    suspend fun refreshAccessToken(refreshToken: String): Pair<String, String>
    //endregion

    //region permission
      suspend fun createRequestPermission(
        deliveryRequestPermission: DeliveryRequestPermission
    ): Boolean
    //endregion

}