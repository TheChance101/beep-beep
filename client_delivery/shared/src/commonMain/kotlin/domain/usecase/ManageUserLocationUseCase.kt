package domain.usecase

import domain.entity.Location
import domain.gateway.local.ILocationGateway
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

interface IManageUserLocationUseCase {
    suspend fun startTracking()
    suspend fun trackCurrentLocation(): Flow<Location>
    suspend fun stopTracking()
}

class ManageUserLocationUseCase(private val locationGateway: ILocationGateway) :
    IManageUserLocationUseCase {
    override suspend fun startTracking() {
        locationGateway.startTracking()
    }

    override suspend fun trackCurrentLocation(): Flow<Location> {
        return locationGateway.getCurrentLocation().distinctUntilChanged()
    }

    override suspend fun stopTracking() {
        locationGateway.stopTracking()
    }

}