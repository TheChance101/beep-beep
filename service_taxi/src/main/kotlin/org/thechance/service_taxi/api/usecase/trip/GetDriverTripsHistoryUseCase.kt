package org.thechance.service_taxi.api.usecase.trip

import org.thechance.service_taxi.domain.entity.Trip

interface GetDriverTripsHistoryUseCase {
    suspend operator fun invoke(driverId: String, page: Int, limit: Int): List<Trip>
}