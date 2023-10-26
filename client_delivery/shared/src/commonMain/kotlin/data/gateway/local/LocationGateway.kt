package data.gateway.local

import data.service.ILocationService
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.DeniedAlwaysException
import domain.entity.Location
import domain.gateway.local.ILocationGateway
import domain.utils.LocationAccessDeniedException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationGateway(
    private val locationService: ILocationService,
    private val locationTracker: LocationTracker
) : ILocationGateway {
    override suspend fun startTracking() {
        try {
            locationTracker.startTracking()
           if (!locationService.isDeviceLocationEnabled()) {
               locationService.openLocationSettings()
           }
        } catch (e: DeniedAlwaysException) {
            throw LocationAccessDeniedException()
        }
    }

    override suspend fun getCurrentLocation(): Flow<Location> {
        return locationTracker.getLocationsFlow().map {
            Location(it.latitude, it.longitude)
        }
    }

    override suspend fun stopTracking() {
        locationTracker.stopTracking()
    }

}