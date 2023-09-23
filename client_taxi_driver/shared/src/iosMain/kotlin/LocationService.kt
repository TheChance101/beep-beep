import data.service.ILocationService
import platform.CoreLocation.CLLocationManager

class LocationService(
    private val locationManger: CLLocationManager = CLLocationManager()
) : ILocationService {
    override suspend fun isGPSEnabled(): Boolean {
        return locationManger.locationServicesEnabled
    }

    override fun openLocationSettings() {
        println("Open location Settings")
    }


}
