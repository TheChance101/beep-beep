package org.thechance.service_taxi.domain.usecase.trip

import org.koin.core.annotation.Single
import org.thechance.service_taxi.api.usecase.trip.GetAllTripsUseCase
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.gateway.TripGateway

@Single
class GetAllTripsUseCaseImpl(private val tripGateway: TripGateway): GetAllTripsUseCase {
    override suspend fun invoke(page: Int, limit: Int): List<Trip> {
        return tripGateway.getAllTrips(page, limit)
    }
}