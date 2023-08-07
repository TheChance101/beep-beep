package org.thechance.service_taxi.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.entity.TripUpdateRequest
import org.thechance.service_taxi.domain.gateway.DataBaseGateway
import org.thechance.service_taxi.domain.util.ResourceNotFoundException
import org.thechance.service_taxi.domain.util.validationTrip

@Single
class CustomerUseCaseImp(
    private val dataBaseGateway: DataBaseGateway
) : CustomerUseCase {
    override suspend fun createTrip(trip: Trip): Boolean {
        validationTrip(trip)
        return dataBaseGateway.addTrip(trip)
    }

    override suspend fun getTripsByClientId(
        clientId: String,
        page: Int,
        limit: Int
    ): List<Trip> {
        return dataBaseGateway.getClientTripsHistory(clientId, page, limit)
    }

    override suspend fun rateTrip(tripId: String, rate: Double) {
        dataBaseGateway.getTripById(tripId) ?: throw ResourceNotFoundException
        dataBaseGateway.updateTrip(TripUpdateRequest(tripId, rate = rate))
    }

    override suspend fun getTripById(tripId: String): Trip {
        return dataBaseGateway.getTripById(tripId) ?: throw ResourceNotFoundException
    }

    override suspend fun getTaxi(taxiId: String): Taxi {
        return dataBaseGateway.getTaxiById(taxiId) ?: throw ResourceNotFoundException
    }

    override suspend fun getAllTaxi(page: Int, limit: Int): List<Taxi> {
        return dataBaseGateway.getAllTaxes(page, limit)
    }
}