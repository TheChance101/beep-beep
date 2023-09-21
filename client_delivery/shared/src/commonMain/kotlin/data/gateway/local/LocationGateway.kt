package data.gateway.local

import dev.icerock.moko.geo.LocationTracker
import domain.entity.Location
import domain.gateway.local.ILocationGateway
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationGateway(private val locationTracker: LocationTracker): ILocationGateway {
    override suspend fun startTracking() {
        locationTracker.startTracking()
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