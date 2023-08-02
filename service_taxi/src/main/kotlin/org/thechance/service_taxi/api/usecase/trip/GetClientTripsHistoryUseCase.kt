package org.thechance.service_taxi.api.usecase.trip

import org.thechance.service_taxi.domain.entity.Trip

interface GetClientTripsHistoryUseCase {
    suspend operator fun invoke(clientId: String): List<Trip>
}