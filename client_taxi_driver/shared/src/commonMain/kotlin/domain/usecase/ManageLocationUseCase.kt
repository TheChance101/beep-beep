package domain.usecase

import data.remote.fakegateway.LocationFakeGateway
import domain.entity.Location
import kotlinx.coroutines.flow.Flow


interface IManageLocationUseCase {
    fun trackCurrentLocation(): Flow<Location>
}

class ManageLocationUseCase(
    private val locationGateway: LocationFakeGateway,
) : IManageLocationUseCase {
    override fun trackCurrentLocation() = locationGateway.trackCurrentLocation()
}