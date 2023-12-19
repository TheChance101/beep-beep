package data.remote.gateway.local

import data.service.ILocationService
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.DeniedAlwaysException
import domain.LocationPermissionDeniedException
import domain.entity.Location
import domain.gateway.ILocationGateway
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationRemoteGateway(
    private val locationService: ILocationService,
    private val locationTracker: LocationTracker
) : ILocationGateway {

override suspend fun startTracking() {
    try {
        locationTracker.startTracking()
        if (!locationService.isGPSEnabled()) {
            locationService.openLocationSettings()
        }
    } catch (e: DeniedAlwaysException) {
        throw LocationPermissionDeniedException(e.message)
    }
}

    override suspend fun trackCurrentLocation(): Flow<Location> {
        return locationTracker.getLocationsFlow().map {
            Location(it.latitude, it.longitude)
        }
    }

    override suspend fun stopTracking() {
        locationTracker.stopTracking()
    }
}