package org.thechance.service_taxi.domain.usecase

import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.Trip

interface CustomerUseCase {
    suspend fun getTripsByClientId(clientId: String, page: Int, limit: Int): List<Trip>
    suspend fun createTrip(trip: Trip): Boolean
    suspend fun rateTrip(tripId: String, rate: Double)
    suspend fun getTripById(tripId: String): Trip
    suspend fun getAllTaxi(page: Int, limit: Int): List<Taxi>
    suspend fun getTaxi(taxiId: String): Taxi?
}