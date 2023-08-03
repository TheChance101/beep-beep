package org.thechance.service_taxi.domain.gateway

import org.thechance.service_taxi.domain.entity.Trip

interface TripGateway {
    suspend fun addTrip(trip: Trip): Boolean
    suspend fun getTripById(tripId: String): Trip
    suspend fun getAllTrips(page: Int, limit: Int): List<Trip>
    suspend fun getDriverTripsHistory(driverId: String, page: Int, limit: Int): List<Trip>
    suspend fun getClientTripsHistory(clientId: String, page: Int, limit: Int): List<Trip>
    suspend fun deleteTrip(tripId: String): Boolean
    suspend fun updateTrip(trip: Trip): Boolean
}