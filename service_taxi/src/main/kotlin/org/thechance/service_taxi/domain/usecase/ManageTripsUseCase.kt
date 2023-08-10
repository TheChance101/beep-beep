package org.thechance.service_taxi.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.entity.TripUpdateRequest
import org.thechance.service_taxi.domain.gateway.DataBaseGateway
import org.thechance.service_taxi.domain.util.ResourceNotFoundException
import org.thechance.service_taxi.domain.util.Validations

interface IManageTripsUseCase {
    suspend fun createTrip(trip: Trip): Boolean
    suspend fun getTrips(page: Int, limit: Int): List<Trip>
    suspend fun rateTrip(tripId: String, rate: Double)
    suspend fun deleteTrip(tripId: String): Boolean
    suspend fun getTripById(tripId: String): Trip
}

@Single
class ManageTripsUseCase(
    private val dataBaseGateway: DataBaseGateway,
    private val validations: Validations
) : IManageTripsUseCase {
    override suspend fun getTrips(page: Int, limit: Int): List<Trip> {
        return dataBaseGateway.getAllTrips(page, limit)
    }

    override suspend fun deleteTrip(tripId: String): Boolean {
        dataBaseGateway.getTripById(tripId) ?: throw ResourceNotFoundException
        return dataBaseGateway.deleteTrip(tripId)
    }

    override suspend fun createTrip(trip: Trip): Boolean {
        validations.validationTrip(trip)
        return dataBaseGateway.addTrip(trip)
    }

    override suspend fun rateTrip(tripId: String, rate: Double) {
        dataBaseGateway.getTripById(tripId) ?: throw ResourceNotFoundException
        dataBaseGateway.updateTrip(TripUpdateRequest(tripId, rate = rate))
    }

    override suspend fun getTripById(tripId: String): Trip {
        return dataBaseGateway.getTripById(tripId) ?: throw ResourceNotFoundException
    }
}