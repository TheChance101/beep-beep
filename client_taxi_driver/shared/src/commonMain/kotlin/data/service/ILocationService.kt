package data.service

interface ILocationService {
    fun isGPSEnabled(): Boolean
    fun openLocationSettings()
}
