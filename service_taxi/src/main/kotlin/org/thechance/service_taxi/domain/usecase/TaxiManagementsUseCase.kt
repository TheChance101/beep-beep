package org.thechance.service_taxi.domain.usecase

import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.Trip

interface TaxiManagementsUseCase {

    //region Trip
    suspend fun getClientTripsHistory(clientId: String, page: Int, limit: Int): List<Trip>

    suspend fun getDriverTripsHistory(driverId: String, page: Int, limit: Int): List<Trip>

    suspend fun getTrips(page: Int, limit: Int): List<Trip>
    //getAllTrips

    suspend fun deleteTrip(tripId: String): Boolean

    //endregion

    //region Taxi

    suspend fun addTaxi(taxi: Taxi): Boolean

    suspend fun deleteTaxi(taxiId: String): Boolean

    suspend fun updateTaxi(taxi: Taxi): Boolean

    suspend fun getAllTaxi(page: Int, limit: Int): List<Taxi>
    //endregion
}