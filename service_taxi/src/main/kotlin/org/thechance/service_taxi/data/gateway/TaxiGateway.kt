package org.thechance.service_taxi.data.gateway

import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import com.mongodb.client.model.Updates
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.thechance.service_taxi.api.dto.taxi.toCollection
import org.thechance.service_taxi.api.dto.taxi.toEntity
import org.thechance.service_taxi.api.dto.trip.toCollection
import org.thechance.service_taxi.api.dto.trip.toEntity
import org.thechance.service_taxi.data.DataBaseContainer
import org.thechance.service_taxi.data.collection.TaxiCollection
import org.thechance.service_taxi.data.collection.TripCollection
import org.thechance.service_taxi.data.utils.isSuccessfullyUpdated
import org.thechance.service_taxi.data.utils.paginate
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.gateway.ITaxiGateway

class TaxiGateway(private val container: DataBaseContainer) : ITaxiGateway {
    // region taxi curd
    override suspend fun addTaxi(taxi: Taxi): Taxi {
        val taxiCollection = taxi.toCollection()
        container.taxiCollection.insertOne(taxiCollection)
        return taxiCollection.toEntity()
    }

    override suspend fun getTaxiById(taxiId: String): Taxi? {
        return container.taxiCollection.findOneById(ObjectId(taxiId))
            ?.takeIf { !it.isDeleted }?.toEntity()
    }

    override suspend fun editTaxi(taxiId: String, taxi: Taxi): Taxi {
        val taxiCollection = taxi.copy(id = taxiId).toCollection()
        container.taxiCollection.updateOne(
            filter = TaxiCollection::id eq ObjectId(taxiId),
            update = Updates.combine(
                set(TaxiCollection::plateNumber setTo taxiCollection.plateNumber),
                set(TaxiCollection::color setTo taxiCollection.color),
                set(TaxiCollection::type setTo taxiCollection.type),
                set(TaxiCollection::driverId setTo taxiCollection.driverId),
                set(TaxiCollection::isAvailable setTo taxiCollection.isAvailable),
                set(TaxiCollection::seats setTo taxiCollection.seats),
            )
        )
        return taxiCollection.toEntity()
    }

    override suspend fun getAllTaxes(page: Int, limit: Int): List<Taxi> {
        return container.taxiCollection.find(TaxiCollection::isDeleted ne true)
            .paginate(page, limit).toList().toEntity()
    }

    override suspend fun deleteTaxi(taxiId: String): Taxi? {
        return container.taxiCollection.findOneAndUpdate(
            filter = TaxiCollection::id eq ObjectId(taxiId),
            update = set(TaxiCollection::isDeleted setTo true)
        )?.toEntity()
    }

    override suspend fun getNumberOfTaxis(): Long {
        return container.taxiCollection.countDocuments(TaxiCollection::isDeleted ne true)
    }

    override suspend fun isTaxiExistedBefore(taxi: Taxi): Boolean {
        val query = TaxiCollection::plateNumber eq taxi.plateNumber
        return container.taxiCollection.findOne(query) != null
    }

    override suspend fun findTaxisWithFilters(
        page: Int,
        limit: Int,
        status: Boolean?,
        color: Long?,
        seats: Int?,
        query: String?
    ): List<Taxi> {
        val searchQueries = or(
            TaxiCollection::plateNumber regex Regex(query.orEmpty(), RegexOption.IGNORE_CASE),
            TaxiCollection::driverUsername regex Regex(query.orEmpty(), RegexOption.IGNORE_CASE),
        )
        val filter = and(
            status?.let { TaxiCollection::isAvailable eq it },
            color?.let { TaxiCollection::color eq it },
            seats?.let { TaxiCollection::seats eq it }
        )
        return container.taxiCollection.find(
            searchQueries,
            filter,
            TaxiCollection::isDeleted ne true
        ).paginate(page, limit).toList().toEntity()
    }

    override suspend fun updateTaxiTripsCount(taxiId: String, count: Int): Taxi? {
        return container.taxiCollection.findOneAndUpdate(
            filter = TaxiCollection::id eq ObjectId(taxiId),
            update = set(TaxiCollection::tripsCount setTo count),
            options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        )?.toEntity()
    }
    //endregion

    //region trip curd
    override suspend fun addTrip(trip: Trip): Trip? {
        container.tripCollection.insertOne(trip.toCollection())
        return getTripById(trip.id)
    }

    override suspend fun getTripById(tripId: String): Trip? {
        return container.tripCollection.findOne(TripCollection::isDeleted ne true)?.toEntity()
    }

    override suspend fun getAllTrips(page: Int, limit: Int): List<Trip> {
        return container.tripCollection.find(TripCollection::isDeleted ne true)
            .paginate(page, limit).toList()
            .toEntity()
    }

    override suspend fun getDriverTripsHistory(
        driverId: String,
        page: Int,
        limit: Int
    ): List<Trip> {
        return container.tripCollection.find(
            and(
                TripCollection::isDeleted ne true,
                TripCollection::driverId eq ObjectId(driverId)
            )
        ).paginate(page, limit).toList().toEntity()
    }

    override suspend fun getClientTripsHistory(
        clientId: String,
        page: Int,
        limit: Int
    ): List<Trip> {
        return container.tripCollection.find(
            and(
                TripCollection::isDeleted ne true,
                TripCollection::clientId eq ObjectId(clientId)
            )
        ).paginate(page, limit).toList().toEntity()
    }

    override suspend fun deleteTrip(tripId: String): Trip? {
        val trip = container.tripCollection.findOneById(ObjectId(tripId))
        container.tripCollection.updateOneById(
            id = ObjectId(tripId),
            update = Updates.set(TripCollection::isDeleted.name, true)
        )
        return trip?.toEntity()
    }

    override suspend fun approveTrip(tripId: String, taxiId: String, driverId: String): Trip? {
        return container.tripCollection.findOneAndUpdate(
            filter = and(
                TripCollection::isDeleted ne true,
                TripCollection::id eq ObjectId(tripId),
            ),
            update = Updates.combine(
                Updates.set(TripCollection::taxiId.name, ObjectId(taxiId)),
                Updates.set(TripCollection::driverId.name, ObjectId(driverId)),
                Updates.set(
                    TripCollection::startDate.name, Clock.System.now().toLocalDateTime(
                        TimeZone.currentSystemDefault()
                    ).toString()
                )
            ),
            options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        )?.toEntity()
    }

    override suspend fun finishTrip(tripId: String, driverId: String): Trip? {
        return container.tripCollection.findOneAndUpdate(
            filter = and(
                TripCollection::isDeleted ne true,
                TripCollection::id eq ObjectId(tripId),
                TripCollection::driverId eq ObjectId(driverId),
            ),
            update = Updates.set(
                TripCollection::endDate.name, Clock.System.now().toLocalDateTime(
                    TimeZone.currentSystemDefault()
                ).toString()
            ),
            options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        )?.toEntity()
    }

    override suspend fun rateTrip(tripId: String, rate: Double): Trip? {
        return container.tripCollection.findOneAndUpdate(
            filter = and(
                TripCollection::isDeleted ne true,
                TripCollection::id eq ObjectId(tripId),
            ),
            update = Updates.set(TripCollection::rate.name, rate),
            options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        )?.toEntity()
    }

    override suspend fun getNumberOfTripsByDriverId(id: String): Long {
        return container.tripCollection.countDocuments(
            and(
                TripCollection::isDeleted ne true,
                TripCollection::driverId eq ObjectId(id)
            )
        )
    }

    override suspend fun getNumberOfTripsByClientId(id: String): Long {
        return container.tripCollection.countDocuments(
            and(
                TripCollection::isDeleted ne true,
                TripCollection::clientId eq ObjectId(id)
            )
        )
    }

    override suspend fun deleteTaxiByDriverId(driverId: String): Boolean {
        return container.taxiCollection.updateMany(
            filter = TaxiCollection::driverId eq ObjectId(driverId),
            update = set(TaxiCollection::isDeleted setTo true),
        ).isSuccessfullyUpdated()
    }
    //endregion
}