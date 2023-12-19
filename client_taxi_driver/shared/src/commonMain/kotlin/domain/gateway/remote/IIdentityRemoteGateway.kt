package domain.gateway.remote

import domain.entity.TaxiRequestPermission
import domain.entity.Session
import domain.entity.Taxi


interface IIdentityRemoteGateway {

    suspend fun loginUser(userName: String, password: String): Session

    // the pair this fun return is <accessToken, refreshToken>
    suspend fun refreshAccessToken(refreshToken: String): Pair<String, String>

    suspend fun createRequestPermission(taxiRequestPermission: TaxiRequestPermission): Boolean
    suspend fun getAllVehicles(): List<Taxi>
}

