package org.thechance.service_taxi.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.gateway.TaxiGateway
import org.thechance.service_taxi.domain.gateway.TripGateway
@Single
class ClientUseCaseImp(
        private val tripGateway: TripGateway,
        private val taxiGateway: TaxiGateway
) : ClientUseCase {
    override suspend fun addTrip(trip: Trip): Boolean {
        return tripGateway.addTrip(trip)
    }

    override suspend fun getTripById(tripId: String): Trip {
        return tripGateway.getTripById(tripId)
    }

    override suspend fun getTaxi(taxiId: String): Taxi? {
        return taxiGateway.getTaxiById(taxiId)
    }

    override suspend fun rateTrip(tripId: String): Boolean {
        TODO("Not yet implemented")
    }
}