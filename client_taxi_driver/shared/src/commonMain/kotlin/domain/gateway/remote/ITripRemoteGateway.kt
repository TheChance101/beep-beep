package domain.gateway.remote

import domain.entity.Location
import domain.entity.Trip
import kotlinx.coroutines.flow.Flow


interface ITripRemoteGateway {
    suspend fun getTrips(): Flow<Trip>
    suspend fun sendLocation(location: Location, tripId: String)
    suspend fun updateTrip(taxiId: String, tripId: String)
}