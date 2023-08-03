package org.thechance.service_taxi.domain.usecase.trip

import org.koin.core.annotation.Single
import org.thechance.service_taxi.api.usecase.trip.UpdateTripUseCase
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.gateway.TripGateway

@Single
class UpdateTripUseCaseImpl(private val tripGateway: TripGateway): UpdateTripUseCase {
    override suspend fun invoke(trip: Trip): Boolean {
        return tripGateway.updateTrip(trip)
    }
}