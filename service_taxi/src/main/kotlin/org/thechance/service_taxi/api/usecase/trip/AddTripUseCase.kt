package org.thechance.service_taxi.api.usecase.trip

import org.thechance.service_taxi.domain.entity.Trip

interface AddTripUseCase {
    suspend operator fun invoke(trip: Trip): Boolean
}