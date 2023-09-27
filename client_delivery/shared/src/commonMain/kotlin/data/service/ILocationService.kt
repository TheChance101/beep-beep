package data.service


interface ILocationService {
    fun isDeviceLocationEnabled(): Boolean
    fun openLocationSettings()
}