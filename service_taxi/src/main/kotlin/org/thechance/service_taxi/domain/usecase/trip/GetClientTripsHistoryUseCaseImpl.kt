package org.thechance.service_taxi.domain.usecase.trip

import org.koin.core.annotation.Single
import org.thechance.service_taxi.api.usecase.trip.GetClientTripsHistoryUseCase
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.gateway.TripGateway

@Single
class GetClientTripsHistoryUseCaseImpl(private val tripGateway: TripGateway): GetClientTripsHistoryUseCase {
    override suspend fun invoke(clientId: String, page: Int, limit: Int): List<Trip> {
        return tripGateway.getClientTripsHistory(clientId, page, limit)
    }
}