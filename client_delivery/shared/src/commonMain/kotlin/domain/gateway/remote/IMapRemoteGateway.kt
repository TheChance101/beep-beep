package domain.gateway.remote

import domain.entity.Location
import domain.entity.Order
import kotlinx.coroutines.flow.Flow


interface IMapRemoteGateway {
    suspend fun getOrders(): Flow<Order>
    suspend fun sendLocation(location: Location, tripId: String)
    suspend fun updateTrip(taxiId: String, tripId: String): Order
}