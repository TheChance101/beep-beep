package org.thechance.service_taxi.domain.usecase

import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.exceptions.AlreadyExistException
import org.thechance.service_taxi.domain.exceptions.ResourceNotFoundException
import org.thechance.service_taxi.domain.gateway.ITaxiGateway

interface IDriverTripsManagementUseCase {
    suspend fun getTripsByDriverId(driverId: String, page: Int, limit: Int): List<Trip> // driver
    suspend fun updateTrip(driverId: String, taxiId: String, tripId: String): Trip
    suspend fun getNumberOfTripsByDriverId(id: String): Long
    suspend fun getActiveTripsByUserId(userId: String): List<Trip>
}

class DriverTripsManagementUseCase(
    private val taxiGateway: ITaxiGateway,
) : IDriverTripsManagementUseCase {
    private suspend fun getTripStatus(tripId: String): Int {
        return taxiGateway.getTripStatus(tripId)
    }

    override suspend fun updateTrip(driverId: String, taxiId: String, tripId: String): Trip {
        val currentTripStatus = Trip.getOrderStatus(getTripStatus(tripId))
        val updatedStatusCode = when (currentTripStatus) {
            Trip.Status.PENDING -> Trip.Status.APPROVED.statusCode
            Trip.Status.APPROVED -> Trip.Status.RECEIVED.statusCode
            Trip.Status.RECEIVED -> Trip.Status.FINISHED.statusCode
            Trip.Status.FINISHED -> throw AlreadyExistException()
        }
        return taxiGateway.updateTrip(
            driverId = driverId,
            taxiId = taxiId,
            tripId = tripId,
            statusCode = updatedStatusCode
        ) ?: throw ResourceNotFoundException()
    }

    override suspend fun getNumberOfTripsByDriverId(id: String): Long {
        return taxiGateway.getNumberOfTripsByDriverId(id)
    }

    override suspend fun getActiveTripsByUserId(userId: String): List<Trip> {
        return taxiGateway.getActiveTripsByUserId(userId)
    }

    override suspend fun getTripsByDriverId(driverId: String, page: Int, limit: Int): List<Trip> {
        return taxiGateway.getDriverTripsHistory(driverId, page, limit)
    }
}