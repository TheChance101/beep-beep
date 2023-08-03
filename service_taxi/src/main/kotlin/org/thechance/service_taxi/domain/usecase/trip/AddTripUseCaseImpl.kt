package org.thechance.service_taxi.domain.usecase.trip

import org.koin.core.annotation.Single
import org.thechance.service_taxi.api.usecase.trip.AddTripUseCase
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.gateway.TripGateway

@Single
class AddTripUseCaseImpl(private val tripGateway: TripGateway): AddTripUseCase {
    override suspend fun invoke(trip: Trip): Boolean {
        return tripGateway.addTrip(trip)
    }
}