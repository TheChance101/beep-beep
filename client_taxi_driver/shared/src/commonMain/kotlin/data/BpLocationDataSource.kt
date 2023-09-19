package data

import dev.icerock.moko.geo.LocationTracker
import domain.dataSource.IBpLocationDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BpLocationDataSource(private val locationTracker: LocationTracker) : IBpLocationDataSource {

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
