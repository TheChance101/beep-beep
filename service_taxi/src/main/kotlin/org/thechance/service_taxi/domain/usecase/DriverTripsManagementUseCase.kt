package org.thechance.service_taxi.domain.usecase

import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.exceptions.ResourceNotFoundException
import org.thechance.service_taxi.domain.gateway.DataBaseGateway

interface IDriverTripsManagementUseCase {
    suspend fun getTripsByDriverId(driverId: String, page: Int, limit: Int): List<Trip> // driver
    suspend fun approveTrip(driverId: String, taxiId: String, tripId: String): Trip // driver
    suspend fun finishTrip(driverId: String, tripId: String): Trip // driver
}

class DriverTripsManagementUseCase(
    private val dataBaseGateway: DataBaseGateway,
) : IDriverTripsManagementUseCase {
    override suspend fun approveTrip(driverId: String, taxiId: String, tripId: String): Trip {
        dataBaseGateway.getTripById(tripId) ?: throw ResourceNotFoundException
        return dataBaseGateway.approveTrip(tripId, taxiId, driverId) ?: throw ResourceNotFoundException
    }

    override suspend fun finishTrip(driverId: String, tripId: String): Trip {
        dataBaseGateway.getTripById(tripId) ?: throw ResourceNotFoundException
        return dataBaseGateway.finishTrip(tripId, driverId) ?: throw ResourceNotFoundException
    }

    override suspend fun getTripsByDriverId(
        driverId: String,
        page: Int,
        limit: Int
    ): List<Trip> {
        return dataBaseGateway.getDriverTripsHistory(driverId, page, limit)
    }


}