package org.thechance.service_taxi.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.thechance.service_taxi.domain.entity.Color
import org.thechance.service_taxi.domain.entity.Location
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.gateway.ITaxiGateway


object FakeGateway : ITaxiGateway {
    var taxes = mutableListOf(
        Taxi(
            id = "64d111a60f294c4b8f718973",
            plateNumber = "1234 ABC",
            color = Color.BLACK,
            type = "Sedan",
            driverId = "123456789123456789123471",
            isAvailable = true,
            seats = 4
        )
    )
    var trips = mutableListOf(
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

    override suspend fun addTaxi(taxi: Taxi): Taxi {
        taxes.add(taxi); return taxes[taxes.indexOf(taxi)]
    }

    override suspend fun getTaxiById(taxiId: String): Taxi? {
        return taxes.find { it.id == taxiId }
    }

    override suspend fun editTaxi(taxiId: String, taxi: Taxi): Taxi {
        val index = taxes.indexOf(getTaxiById(taxiId))
        taxes[index] = taxi
        return taxes[index]
    }

    override suspend fun getAllTaxes(page: Int, limit: Int): List<Taxi> {
        return taxes.toList()
    }

    override suspend fun deleteTaxi(taxiId: String): Taxi? {
        val taxi = getTaxiById(taxiId)
        taxes.remove(taxi)
        return taxi
    }

    override suspend fun getNumberOfTaxis(): Long {
        return taxes.size.toLong()
    }

    override suspend fun addTrip(trip: Trip): Trip? {
        trips.add(trip)
        return getTripById(trip.id)
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

    override suspend fun deleteTrip(tripId: String): Trip? {
        val trip = getTripById(tripId)
        trips.remove(trip)
        return trip
    }

    override suspend fun approveTrip(tripId: String, taxiId: String, driverId: String): Trip? {
        val target = trips.find { it.id == tripId } ?: return null
        val position = trips.indexOf(target)
        trips.removeAt(position)
        trips.add(
            position,
            target.copy(
                taxiId = taxiId,
                driverId = driverId,
                startDate = Clock.System.now().toLocalDateTime(
                    TimeZone.currentSystemDefault()
                )
            )
        )
        return trips[position]
    }

    override suspend fun finishTrip(tripId: String, driverId: String): Trip? {
        val target = trips.find { it.id == tripId } ?: return null
        val position = trips.indexOf(target)
        trips.removeAt(position)
        trips.add(
            position,
            target.copy(
                endDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )
        )
        return trips[position]
    }

    override suspend fun rateTrip(tripId: String, rate: Double): Trip? {
        val target = trips.find { it.id == tripId } ?: return null
        val position = trips.indexOf(target)
        trips.removeAt(position)
        trips.add(
            position,
            target.copy(
                rate = rate
            )
        )
        return trips[position]
    }

    override suspend fun getNumberOfTripsByDriverId(id: String): Long {
        return trips.filter { it.driverId == id }.size.toLong()
    }

    override suspend fun getNumberOfTripsByClientId(id: String): Long {
        return trips.filter { it.clientId == id }.size.toLong()
    }

    fun reset() {
        taxes = mutableListOf(
            Taxi(
                id = "64d111a60f294c4b8f718973",
                plateNumber = "1234 ABC",
                color = Color.BLACK,
                type = "Sedan",
                driverId = "123456789123456789123471",
                isAvailable = true,
                seats = 4
            )
        )
        trips = mutableListOf(
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
    }
}