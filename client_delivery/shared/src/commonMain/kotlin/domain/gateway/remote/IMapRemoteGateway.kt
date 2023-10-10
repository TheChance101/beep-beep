package domain.gateway.remote

import data.remote.model.LocationDto
import domain.entity.Trip


interface IMapRemoteGateway {
    suspend fun sendLocation(location: LocationDto, tripId: String)
    suspend fun getTripById(tripId: String): Trip
    suspend fun approveTrip(taxiId: String, tripId: String, driverId: String): Trip
    suspend fun finishTrip(tripId: String, driverId: String): Trip
}