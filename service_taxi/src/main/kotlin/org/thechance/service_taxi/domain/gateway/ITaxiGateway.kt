package org.thechance.service_taxi.domain.gateway

import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.Trip

interface ITaxiGateway {
    //region taxi curd
    suspend fun addTaxi(taxi: Taxi): Taxi
    suspend fun getTaxiById(taxiId: String): Taxi?
    suspend fun getTaxiByDriverId(driverId: String): Taxi?
    suspend fun editTaxi(taxiId: String, taxi: Taxi): Taxi
    suspend fun getAllTaxes(page: Int, limit: Int): List<Taxi>
    suspend fun getTaxisForDriver(driverId: String): List<Taxi>
    suspend fun deleteTaxi(taxiId: String): Taxi?
    suspend fun getNumberOfTaxis(): Long
    suspend fun isTaxiExistedBefore(taxi: Taxi): Boolean
    suspend fun findTaxisWithFilters(
        page: Int,
        limit: Int,
        status: Boolean?,
        color: Long?,
        seats: Int?,
        query: String?
    ): List<Taxi>

    suspend fun updateTaxiTripsCount(taxiId: String, count: Int): Taxi?
    //endregion

    //region trips curd
    suspend fun addTrip(trip: Trip): Trip?
    suspend fun getTripById(tripId: String): Trip?
    suspend fun getTripByOrderId(orderId: String): Trip?
    suspend fun getAllTrips(page: Int, limit: Int): List<Trip>
    suspend fun getActiveTripsByUserId(userId: String): List<Trip>
    suspend fun getDriverTripsHistory(driverId: String, page: Int, limit: Int): List<Trip>
    suspend fun getClientTripsHistory(clientId: String, page: Int, limit: Int): List<Trip>
    suspend fun getTripStatus(tripId: String): Int
    suspend fun updateTrip(driverId: String, taxiId: String, tripId: String, statusCode: Int): Trip?
    suspend fun rateTrip(tripId: String, rate: Double): Trip?
    suspend fun getNumberOfTripsByDriverId(id: String): Long
    suspend fun getNumberOfTripsByClientId(id: String): Long
    suspend fun deleteTaxiByDriverId(driverId: String): Boolean
    //endregion
    suspend fun deleteAllCollections():Boolean
}