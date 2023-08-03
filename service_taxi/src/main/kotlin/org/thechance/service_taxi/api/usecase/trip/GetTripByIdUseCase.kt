package org.thechance.service_taxi.api.usecase.trip

import org.thechance.service_taxi.domain.entity.Trip

interface GetTripByIdUseCase {
    suspend operator fun invoke(tripId: String): Trip
}