package domain.usecase

import data.remote.fakegateway.LocationFakeGateway
import data.remote.fakegateway.MapFakeGateway
import domain.entity.Location
import domain.entity.Order
import kotlinx.coroutines.flow.Flow


interface IManageOrderUseCase {
    suspend fun foundNewOrder(): Order

    fun getLiveLocation(): Flow<Location>
}

class ManageOrderUseCase(
    private val mapGateway: MapFakeGateway,
    private val locationGateway: LocationFakeGateway,
) : IManageOrderUseCase {
    override suspend fun foundNewOrder() = mapGateway.findingNewOrder()
    override fun getLiveLocation() = locationGateway.streamLiveLocation()
}