package org.thechance.service_taxi.domain

import org.thechance.service_taxi.domain.entity.Location
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.TaxiUpdateRequest
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.entity.TripUpdateRequest
import org.thechance.service_taxi.domain.gateway.DataBaseGateway


object FakeGateway : DataBaseGateway {
    private val taxes = mutableListOf(
        Taxi(
            id = "64d111a60f294c4b8f718973",
            plateNumber = "1234 ABC",
            color = "Blue",
            type = "Sedan",
            driverId = "123456789123456789123471",
            isAvailable = true,
            seats = 4
        )
    )
    private val trips = mutableListOf(
        Trip(
            id = "64d111a60f294c4b8f718973",
            taxiId = null,
            driverId = null,
            clientId = "123456789123456789123471",
            startPoint = Location(30.0, 150.0),
            destination = Location(50.0, 170.0),
            price = 100.0,
        )
    )

    override suspend fun addTaxi(taxi: Taxi): Boolean {
        taxes.add(taxi); return true
    }

    override suspend fun getTaxiById(taxiId: String): Taxi? {
        return taxes.find { it.id == taxiId }
    }

    override suspend fun getAllTaxes(page: Int, limit: Int): List<Taxi> {
        return taxes.toList()
    }

    override suspend fun deleteTaxi(taxiId: String): Boolean {
        return getTaxiById(taxiId)?.let { taxes.remove(it); true } ?: false
    }

    override suspend fun updateTaxi(taxi: TaxiUpdateRequest): Boolean {
        val target = taxes.find { it.id == taxi.id } ?: return false
        val position = taxes.indexOf(target)
        taxes.removeAt(position)
        taxes.add(
            position, target.copy(
                plateNumber = taxi.plateNumber ?: target.plateNumber,
                color = taxi.color ?: target.color,
                type = taxi.type ?: target.type,
                driverId = taxi.driverId ?: target.driverId,
                isAvailable = taxi.isAvailable ?: target.isAvailable,
                seats = taxi.seats ?: target.seats,
            )
        )
        return true
    }

    override suspend fun addTrip(trip: Trip): Boolean {
        trips.add(trip); return true
    }

    override suspend fun getTripById(tripId: String): Trip? {
        return trips.find { it.id == tripId }
    }

    override suspend fun getAllTrips(page: Int, limit: Int): List<Trip> {
        return trips.toList()
    }

    override suspend fun getDriverTripsHistory(
        driverId: String,
        page: Int,
        limit: Int
    ): List<Trip> {
        return trips.filter { it.driverId == driverId }
    }

    override suspend fun getClientTripsHistory(
        clientId: String,
        page: Int,
        limit: Int
    ): List<Trip> {
        return trips.filter { it.clientId == clientId }
    }

    override suspend fun deleteTrip(tripId: String): Boolean {
        getTripById(tripId)?.let { trips.remove(it); return true }; return false
    }

    override suspend fun updateTrip(trip: TripUpdateRequest): Boolean {
        val target = trips.find { it.id == trip.id } ?: return false
        val position = trips.indexOf(target)
        trips.removeAt(position)
        trips.add(
            position,
            target.copy(
                taxiId = trip.taxiId ?: target.taxiId,
                driverId = trip.driverId ?: target.driverId,
                clientId = trip.clientId ?: target.clientId,
                startPoint = trip.startPoint ?: target.startPoint,
                destination = trip.destination ?: target.destination,
                rate = trip.rate ?: target.rate,
                price = trip.price ?: target.price,
                startDate = trip.startDate ?: target.startDate,
                endDate = trip.endDate ?: target.endDate,
            )
        )
        return true
    }
}