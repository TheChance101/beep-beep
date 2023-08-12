package org.thechance.service_taxi.domain.usecase

import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.exceptions.ResourceNotFoundException
import org.thechance.service_taxi.domain.gateway.DataBaseGateway

interface IManageTripsUseCase {
    suspend fun getTrips(page: Int, limit: Int): List<Trip> // admin
    suspend fun deleteTrip(tripId: String): Boolean // admin
    suspend fun getTripById(tripId: String): Trip // admin
}

class ManageTripsUseCase(
    private val dataBaseGateway: DataBaseGateway
) : IManageTripsUseCase {
    override suspend fun getTrips(page: Int, limit: Int): List<Trip> {
        return dataBaseGateway.getAllTrips(page, limit)
    }

    override suspend fun deleteTrip(tripId: String): Boolean {
        dataBaseGateway.getTripById(tripId) ?: throw ResourceNotFoundException
        return dataBaseGateway.deleteTrip(tripId)
    }

    override suspend fun getTripById(tripId: String): Trip {
        return dataBaseGateway.getTripById(tripId) ?: throw ResourceNotFoundException
    }
}