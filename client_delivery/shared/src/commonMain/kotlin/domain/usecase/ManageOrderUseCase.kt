package domain.usecase

import data.remote.model.LocationDto
import domain.entity.Order
import domain.gateway.remote.IMapRemoteGateway
import kotlinx.coroutines.flow.Flow

interface IManageOrderUseCase {
    suspend fun getOrders(): Flow<Order>
    suspend fun sendLocation(location: LocationDto, tripId: String)
    suspend fun acceptOrder(taxiId: String, tripId: String, driverId: String): Order
    suspend fun updateOrderAsReceived(tripId: String, driverId: String): Order
    suspend fun updateOrderAsDelivered(tripId: String, driverId: String): Order
}

class ManageOrderUseCase(private val remoteGateway: IMapRemoteGateway) : IManageOrderUseCase {
    override suspend fun getOrders(): Flow<Order> {
        return remoteGateway.getOrders()
    }

    override suspend fun sendLocation(location: LocationDto, tripId: String) {
        remoteGateway.sendLocation(location, tripId)
    }

    override suspend fun acceptOrder(taxiId: String, tripId: String, driverId: String): Order {
        return remoteGateway.acceptOrder(taxiId, tripId, driverId)
    }

    override suspend fun updateOrderAsReceived(tripId: String, driverId: String): Order {
        return remoteGateway.updateOrderAsReceived(tripId, driverId)
    }

    override suspend fun updateOrderAsDelivered(tripId: String, driverId: String): Order {
        return remoteGateway.updateOrderAsDelivered(tripId, driverId)
    }


}