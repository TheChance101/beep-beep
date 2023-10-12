package domain.gateway.remote

import data.remote.model.LocationDto
import domain.entity.Order
import kotlinx.coroutines.flow.Flow


interface IMapRemoteGateway {
    suspend fun getOrders(deliveryId:String): Flow<Order>
    suspend fun sendLocation(location: LocationDto, tripId: String)
    suspend fun acceptOrder(taxiId: String, tripId: String, driverId: String): Order
    suspend fun updateOrderAsReceived(tripId: String, driverId: String): Order
    suspend fun updateOrderAsDelivered(tripId: String, driverId: String): Order
}