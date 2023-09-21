package domain.usecase

import data.remote.fakegateway.LocationFakeGateway
import domain.entity.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged


interface IManageLocationUseCase {
    suspend fun trackCurrentLocation(): Flow<Location>
}

class ManageLocationUseCase(
    private val locationGateway: LocationFakeGateway,
) : IManageLocationUseCase {
    override suspend fun trackCurrentLocation() =
        locationGateway.trackCurrentLocation().distinctUntilChanged()
}