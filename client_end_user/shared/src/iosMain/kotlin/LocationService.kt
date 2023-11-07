import data.gateway.service.ILocationService
import platform.CoreLocation.CLLocationManager
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

class LocationService(
    private val locationManger: CLLocationManager = CLLocationManager()
) : ILocationService {

    override fun isDeviceLocationEnabled(): Boolean {
        return locationManger.locationServicesEnabled
    }

    override fun openLocationSettings() {
        NSURL.URLWithString(UIApplicationOpenSettingsURLString)
            ?.let { UIApplication.sharedApplication.openURL(it) }
    }


}
