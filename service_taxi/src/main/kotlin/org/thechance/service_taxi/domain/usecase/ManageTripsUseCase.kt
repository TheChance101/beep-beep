package org.thechance.service_taxi.domain.usecase

import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.exceptions.ResourceNotFoundException
import org.thechance.service_taxi.domain.gateway.ITaxiGateway

interface IManageTripsUseCase {
    suspend fun getTrips(page: Int, limit: Int): List<Trip> // admin
    suspend fun deleteTrip(tripId: String): Trip // admin
    suspend fun getTripById(tripId: String): Trip // admin
}

class ManageTripsUseCase(
    private val ITaxiGateway: ITaxiGateway
) : IManageTripsUseCase {
    override suspend fun getTrips(page: Int, limit: Int): List<Trip> {
        return ITaxiGateway.getAllTrips(page, limit)
    }

    override suspend fun deleteTrip(tripId: String): Trip {
        ITaxiGateway.getTripById(tripId) ?: throw ResourceNotFoundException
        return ITaxiGateway.deleteTrip(tripId) ?: throw ResourceNotFoundException
    }

    override suspend fun getTripById(tripId: String): Trip {
        return ITaxiGateway.getTripById(tripId) ?: throw ResourceNotFoundException
    }
}