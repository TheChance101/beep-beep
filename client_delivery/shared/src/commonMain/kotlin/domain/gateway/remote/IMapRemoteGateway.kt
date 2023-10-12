package domain.gateway.remote

import data.remote.model.LocationDto
import domain.entity.Trip
import kotlinx.coroutines.flow.Flow


interface IMapRemoteGateway {
    suspend fun getOrders(): Flow<Trip>
    suspend fun sendLocation(location: LocationDto, tripId: String)
    suspend fun approveTrip(taxiId: String, tripId: String, driverId: String): Trip
    suspend fun finishTrip(tripId: String, driverId: String): Trip
}