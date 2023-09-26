package org.thechance.service_taxi.domain.gateway

import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.Trip

interface ITaxiGateway {
    //region taxi curd
    suspend fun addTaxi(taxi: Taxi): Taxi
    suspend fun getTaxiById(taxiId: String): Taxi?
    suspend fun editTaxi(taxiId: String, taxi: Taxi): Taxi
    suspend fun getAllTaxes(page: Int, limit: Int): List<Taxi>
    suspend fun deleteTaxi(taxiId: String): Taxi?
    suspend fun getNumberOfTaxis(): Long
    suspend fun isTaxiExistedBefore(taxi: Taxi): Boolean
    suspend fun findTaxisWithFilters(page: Int, limit: Int, status: Boolean?, color: Long?, seats: Int?, query: String?) : List<Taxi>
    suspend fun updateTaxiTripsCount(taxiId: String, count: Int): Taxi?
    //endregion

    //region trips curd
    suspend fun addTrip(trip: Trip): Trip?
    suspend fun getTripById(tripId: String): Trip?
    suspend fun getAllTrips(page: Int, limit: Int): List<Trip>
    suspend fun getDriverTripsHistory(driverId: String, page: Int, limit: Int): List<Trip>
    suspend fun getClientTripsHistory(clientId: String, page: Int, limit: Int): List<Trip>
    suspend fun deleteTrip(tripId: String): Trip?
    suspend fun approveTrip(tripId: String, taxiId: String, driverId: String): Trip?
    suspend fun finishTrip(tripId: String, driverId: String): Trip?
    suspend fun rateTrip(tripId: String, rate: Double): Trip?
    suspend fun getNumberOfTripsByDriverId(id: String): Long
    suspend fun getNumberOfTripsByClientId(id: String): Long
    suspend fun deleteTaxiByDriverId(driverId: String): Boolean
    //endregion
}