package domain.usecase

import domain.gateway.ILocationDataSource
import kotlinx.coroutines.flow.Flow

interface IManageCurrentLocationUseCase {
    suspend fun trackCurrentLocation(): Flow<Pair<Double, Double>>
    suspend fun stopTrackingLocation()
}

class ManageCurrentLocationUseCase(
    private val currentLocation: ILocationDataSource
) : IManageCurrentLocationUseCase {

    override suspend fun trackCurrentLocation(): Flow<Pair<Double, Double>> {
        return currentLocation.getCurrentLocation()
    }

    override suspend fun stopTrackingLocation() {
        currentLocation.stopTracking()
    }

}