package org.thechance.service_taxi.domain.usecase

import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.exceptions.INVALID_DATE
import org.thechance.service_taxi.domain.exceptions.INVALID_ID
import org.thechance.service_taxi.domain.exceptions.INVALID_LOCATION
import org.thechance.service_taxi.domain.exceptions.INVALID_PRICE
import org.thechance.service_taxi.domain.exceptions.INVALID_RATE
import org.thechance.service_taxi.domain.exceptions.MultiErrorException
import org.thechance.service_taxi.domain.exceptions.ResourceNotFoundException
import org.thechance.service_taxi.domain.gateway.DataBaseGateway
import org.thechance.service_taxi.domain.usecase.utils.IValidations

interface IClientTripsManagementUseCase {
    suspend fun getTripsByClientId(clientId: String, page: Int, limit: Int): List<Trip> // user
    suspend fun rateTrip(tripId: String, rate: Double): Trip // user
    suspend fun createTrip(trip: Trip): Trip // user
}

class ClientTripsManagementUseCase(
    private val dataBaseGateway: DataBaseGateway,
    private val validations: IValidations
) : IClientTripsManagementUseCase {

    override suspend fun getTripsByClientId(
        clientId: String,
        page: Int,
        limit: Int
    ): List<Trip> {
        return dataBaseGateway.getClientTripsHistory(clientId, page, limit)
    }

    override suspend fun rateTrip(tripId: String, rate: Double): Trip {
        dataBaseGateway.getTripById(tripId) ?: throw ResourceNotFoundException
        if (!validations.isValidRate(rate)) throw MultiErrorException(listOf(INVALID_RATE))
        return dataBaseGateway.rateTrip(tripId, rate) ?: throw ResourceNotFoundException
    }

    override suspend fun createTrip(trip: Trip): Trip {
        validationTrip(trip)
        return dataBaseGateway.addTrip(trip) ?: throw ResourceNotFoundException
    }

    private fun validationTrip(trip: Trip) {
        val validationErrors = mutableListOf<Int>()

        if (!validations.isValidId(trip.id)) {
            validationErrors.add(INVALID_ID)
        }
        if (!validations.isValidId(trip.clientId)) {
            validationErrors.add(INVALID_ID)
        }
        trip.taxiId?.let {
            if (!validations.isValidId(it)) {
                validationErrors.add(INVALID_ID)
            }
        }
        trip.driverId?.let {
            if (!validations.isValidId(it)) {
                validationErrors.add(INVALID_ID)
            }
        }
        if (!validations.isValidLocation(trip.startPoint.latitude, trip.startPoint.longitude)) {
            validationErrors.add(INVALID_LOCATION)
        }
        trip.destination?.let {
            if (!validations.isValidLocation(it.latitude, it.longitude)) {
                validationErrors.add(INVALID_LOCATION)
            }
        }
        trip.rate?.let {
            if (!validations.isValidRate(it)) {
                validationErrors.add(INVALID_RATE)
            }
        }
        if (!validations.isValidPrice(trip.price)) {
            validationErrors.add(INVALID_PRICE)
        }
        if (trip.startDate != null && trip.endDate != null) {
            if (trip.startDate >= trip.endDate) {
                validationErrors.add(INVALID_DATE)
            }
        }
        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }
}