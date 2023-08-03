package org.thechance.service_taxi.domain.usecase.trip

import org.koin.core.annotation.Single
import org.thechance.service_taxi.api.usecase.trip.GetDriverTripsHistoryUseCase
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.gateway.TripGateway

@Single
class GetDriverTripsHistoryUseCaseImpl(private val tripGateway: TripGateway): GetDriverTripsHistoryUseCase {
    override suspend fun invoke(driverId: String, page: Int, limit: Int): List<Trip> {
        return tripGateway.getDriverTripsHistory(driverId, page, limit)
    }
}