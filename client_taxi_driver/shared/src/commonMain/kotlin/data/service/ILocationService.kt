package data.service

interface ILocationService {
    suspend fun isGPSEnabled(): Boolean
    fun openLocationSettings()
}
