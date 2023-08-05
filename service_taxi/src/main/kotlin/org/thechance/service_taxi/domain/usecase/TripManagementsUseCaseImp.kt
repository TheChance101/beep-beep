package org.thechance.service_taxi.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.gateway.TripGateway

@Single
class TripManagementsUseCaseImp(
        private val tripGateway: TripGateway,
):TripManagementsUseCase {
    override suspend fun updateTripState(trip: Trip): Boolean {
        return tripGateway.updateTrip(trip)
    }

    override suspend fun getAllTripsByDriverId(driverId: String, page: Int, limit: Int): List<Trip> {
        return tripGateway.getAllTrips(page, limit)//todo
    }

}