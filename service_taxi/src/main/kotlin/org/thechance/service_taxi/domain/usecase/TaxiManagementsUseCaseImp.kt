package org.thechance.service_taxi.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.gateway.TaxiGateway
import org.thechance.service_taxi.domain.gateway.TripGateway

@Single
class TaxiManagementsUseCaseImp(
        private val tripGateway: TripGateway,
        private val taxiGateway: TaxiGateway
) : TaxiManagementsUseCase {
    override suspend fun getClientTripsHistory(clientId: String, page: Int, limit: Int): List<Trip> {
        return tripGateway.getClientTripsHistory(clientId, page, limit)
    }

    override suspend fun getDriverTripsHistory(driverId: String, page: Int, limit: Int): List<Trip> {
        return tripGateway.getDriverTripsHistory(driverId, page, limit)
    }

    override suspend fun getTrips(page: Int, limit: Int): List<Trip> {
        return tripGateway.getAllTrips(page, limit)
    }

    override suspend fun deleteTrip(tripId: String): Boolean {
        return tripGateway.deleteTrip(tripId)
    }

    override suspend fun addTaxi(taxi: Taxi): Boolean {
        return taxiGateway.addTaxi(taxi)
    }

    override suspend fun deleteTaxi(taxiId: String): Boolean {
        return taxiGateway.deleteTaxi(taxiId)
    }

    override suspend fun updateTaxi(taxi: Taxi): Boolean {
        return taxiGateway.updateTaxi(taxi)
    }

    override suspend fun getAllTaxi(page: Int, limit: Int): List<Taxi> {
        return taxiGateway.getAllTaxes(page, limit)
    }
}