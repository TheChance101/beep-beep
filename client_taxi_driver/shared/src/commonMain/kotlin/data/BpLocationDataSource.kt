package data

import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import domain.LocationPermissionDeniedAlwaysException
import domain.LocationPermissionDeniedException
import domain.dataSource.IBpLocationDataSource
import domain.entity.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BpLocationDataSource(private val locationTracker: LocationTracker) : IBpLocationDataSource {

    override suspend fun getCurrentLocation(): Flow<Location> {
        try {
            locationTracker.startTracking()
            return locationTracker.getLocationsFlow().map {
                Location(
                    lat = it.latitude,
                    lng = it.longitude,
                    addressName = ""
                )
            }
        } catch (deniedAlways: DeniedAlwaysException) {
            throw LocationPermissionDeniedAlwaysException(deniedAlways.message)
        } catch (denied: DeniedException) {
            throw LocationPermissionDeniedException(denied.message)
        }
    }

    override fun stopTracking() {
        locationTracker.stopTracking()
    }
}
