package domain.usecase

import data.remote.gateway.local.LocationRemoteGateway
import domain.entity.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged


interface IManageLocationUseCase {
    suspend fun startTracking()
    suspend fun trackCurrentLocation(): Flow<Location>
    suspend fun stopTracking()
}

class ManageLocationUseCase(
    private val locationGateway: LocationRemoteGateway,
) : IManageLocationUseCase {

    override suspend fun startTracking() {
        locationGateway.startTracking()
    }

    override suspend fun stopTracking() {
        locationGateway.stopTracking()
    }
    override suspend fun trackCurrentLocation() =
        locationGateway.trackCurrentLocation().distinctUntilChanged()
}