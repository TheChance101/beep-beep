package org.thechance.service_taxi.domain.usecase.trip

import org.koin.core.annotation.Single
import org.thechance.service_taxi.api.usecase.trip.DeleteTripUseCase
import org.thechance.service_taxi.domain.gateway.TripGateway

@Single
class DeleteTripUseCaseImpl(private val tripGateway: TripGateway): DeleteTripUseCase {
    override suspend fun invoke(tripId: String): Boolean {
        return tripGateway.deleteTrip(tripId)
    }
}