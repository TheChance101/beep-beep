package org.thechance.service_taxi.api.usecase.trip

import org.thechance.service_taxi.domain.entity.Trip

interface GetAllTripsUseCase {
    suspend operator fun invoke(): List<Trip>
}