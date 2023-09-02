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
    private val taxiGateway: ITaxiGateway
) : IManageTripsUseCase {
    override suspend fun getTrips(page: Int, limit: Int): List<Trip> {
        return taxiGateway.getAllTrips(page, limit)
    }

    override suspend fun deleteTrip(tripId: String): Trip {
        taxiGateway.getTripById(tripId) ?: throw ResourceNotFoundException
        return taxiGateway.deleteTrip(tripId) ?: throw ResourceNotFoundException
    }

    override suspend fun getTripById(tripId: String): Trip {
        return taxiGateway.getTripById(tripId) ?: throw ResourceNotFoundException
    }
}