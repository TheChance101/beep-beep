package domain.usecase

import domain.gateway.IBpLocationDataSource
import kotlinx.coroutines.flow.Flow

interface IManageCurrentLocationUseCase {
    suspend fun trackCurrentLocation(): Flow<Pair<Double, Double>>
    suspend fun stopTrackingLocation()
}

class ManageCurrentLocationUseCase(
    private val currentLocation: IBpLocationDataSource
) : IManageCurrentLocationUseCase {

    override suspend fun trackCurrentLocation(): Flow<Pair<Double, Double>> {
        return currentLocation.getCurrentLocation()
    }

    override suspend fun stopTrackingLocation() {
        currentLocation.stopTracking()
    }

}