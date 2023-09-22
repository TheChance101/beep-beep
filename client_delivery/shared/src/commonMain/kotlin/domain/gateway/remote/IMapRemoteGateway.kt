package domain.gateway.remote

import data.remote.model.LocationDto
import data.remote.model.TripDto

//TODO IMPORTANT, AFTER ADDING THE ENTITY RETURN TRIP INSTEAD OF TRIPDTO

interface IMapRemoteGateway {
    suspend fun sendLocation(location: LocationDto, tripId: String)
    suspend fun getTripById(tripId: String): TripDto
    suspend fun approveTrip(taxiId: String, tripId: String, driverId: String): TripDto
    suspend fun finishTrip(tripId: String, driverId: String): TripDto
    suspend fun deleteTrip(tripId: String): TripDto
}