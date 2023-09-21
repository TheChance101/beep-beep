package data.gateway.remote

import dev.icerock.moko.geo.LocationTracker
import domain.gateway.ILocationDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationDataSource(private val locationTracker: LocationTracker) : ILocationDataSource {

    override suspend fun getCurrentLocation(): Flow<Pair<Double, Double>> {
        locationTracker.startTracking()
        return locationTracker.getLocationsFlow().map {
            Pair(it.latitude, it.longitude)
        }
    }

    override fun stopTracking() {
        locationTracker.stopTracking()
    }
}
