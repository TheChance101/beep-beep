package org.thechance.common.domain.getway



interface IRemoteGateway {

    suspend fun getUserData(): String

    suspend fun getPdfTaxiReport()

    suspend fun getCurrentLocation(): String

    suspend fun loginUser(username: String, password: String): Pair<String, String>

}