package org.thechance.service_taxi.domain.gateway

import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.TaxiUpdateRequest
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.entity.TripUpdateRequest

interface DataBaseGateway {
    //region taxi curd
    suspend fun addTaxi(taxi: Taxi): Boolean
    suspend fun getTaxiById(taxiId: String): Taxi?
    suspend fun getAllTaxes(page: Int, limit: Int): List<Taxi>
    suspend fun deleteTaxi(taxiId: String): Boolean
    suspend fun updateTaxi(taxi: TaxiUpdateRequest): Boolean
    //endregion

    //region trips curd
    suspend fun addTrip(trip: Trip): Boolean
    suspend fun getTripById(tripId: String): Trip
    suspend fun getAllTrips(page: Int, limit: Int): List<Trip>
    suspend fun getDriverTripsHistory(driverId: String, page: Int, limit: Int): List<Trip>
    suspend fun getClientTripsHistory(clientId: String, page: Int, limit: Int): List<Trip>
    suspend fun deleteTrip(tripId: String): Boolean
    suspend fun updateTrip(trip: TripUpdateRequest): Boolean
    //endregion
}