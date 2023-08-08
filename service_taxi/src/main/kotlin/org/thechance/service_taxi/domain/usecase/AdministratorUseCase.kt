package org.thechance.service_taxi.domain.usecase

import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.TaxiUpdateRequest
import org.thechance.service_taxi.domain.entity.Trip

interface AdministratorUseCase {
    suspend fun getTrips(page: Int, limit: Int): List<Trip>
    suspend fun deleteTrip(tripId: String): Boolean
    suspend fun createTaxi(taxi: Taxi): Boolean
    suspend fun deleteTaxi(taxiId: String): Boolean
    suspend fun updateTaxi(taxi: TaxiUpdateRequest): Boolean
    suspend fun getAllTaxi(page: Int, limit: Int): List<Taxi>
}