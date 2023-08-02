package org.thechance.service_taxi.domain.usecase.trip

import org.koin.core.annotation.Single
import org.thechance.service_taxi.api.usecase.trip.GetTripByIdUseCase
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.gateway.TripGateway

@Single
class GetTripByIdUseCaseImpl(private val tripGateway: TripGateway): GetTripByIdUseCase {
    override suspend fun invoke(tripId: String): Trip {
        return tripGateway.getTrip(tripId)
    }
}