package domain.gateway.remote

import domain.entity.DeliveryRequestPermission
import domain.entity.Session
import domain.entity.Taxi

interface IIdentityRemoteGateway {
    suspend fun loginUser(userName: String, password: String): Session
    suspend fun refreshAccessToken(refreshToken: String): Pair<String, String>
    suspend fun createRequestPermission(deliveryRequestPermission: DeliveryRequestPermission): Boolean
    suspend fun getAllVehicles(): List<Taxi>


}