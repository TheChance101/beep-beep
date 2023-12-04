package org.thechance.service_taxi.data.gateway

import com.mongodb.client.model.Filters
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import com.mongodb.client.model.Updates
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.thechance.service_taxi.api.dto.taxi.toCollection
import org.thechance.service_taxi.api.dto.taxi.toEntity
import org.thechance.service_taxi.api.dto.trip.toCollection
import org.thechance.service_taxi.api.dto.trip.toEntity
import org.thechance.service_taxi.data.DataBaseContainer
import org.thechance.service_taxi.data.collection.TaxiCollection
import org.thechance.service_taxi.data.collection.TripCollection
import org.thechance.service_taxi.data.collection.relationModel.TripWithTaxi
import org.thechance.service_taxi.data.utils.isSuccessfullyUpdated
import org.thechance.service_taxi.data.utils.paginate
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.exceptions.ResourceNotFoundException
import org.thechance.service_taxi.domain.gateway.ITaxiGateway

class TaxiGateway(private val container: DataBaseContainer) : ITaxiGateway {


    // region taxi curd
    override suspend fun addTaxi(taxi: Taxi): Taxi {
        val taxiCollection = taxi.toCollection()
        container.taxiCollection.insertOne(taxiCollection)
        return taxiCollection.toEntity()
    }

    override suspend fun getTaxiById(taxiId: String): Taxi? {
        return container.taxiCollection.findOneById(ObjectId(taxiId))?.takeIf { !it.isDeleted }?.toEntity()
    }

    override suspend fun getTaxiByDriverId(driverId: String): Taxi? {
        return container.taxiCollection.findOne(
            TaxiCollection::driverId eq ObjectId(driverId)
        )?.takeIf { !it.isDeleted }
            ?.toEntity()
    }

    override suspend fun editTaxi(taxiId: String, taxi: Taxi): Taxi {
        val taxiCollection = taxi.copy(id = taxiId).toCollection()
        container.taxiCollection.updateOne(
            filter = TaxiCollection::id eq ObjectId(taxiId),
            update = Updates.combine(
                set(TaxiCollection::plateNumber setTo taxiCollection.plateNumber),
                set(TaxiCollection::driverUsername setTo taxiCollection.driverUsername),
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

    override suspend fun getTaxisForDriver(driverId: String): List<Taxi> {
        return container.taxiCollection.find(
            and(
                TaxiCollection::isDeleted eq false,
                TaxiCollection::driverId eq ObjectId(driverId)
            )
        ).toList().toEntity()
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
        val insertResult = container.tripCollection.insertOne(trip.toCollection())
        return if (insertResult.wasAcknowledged()) {
            trip
        } else {
            null
        }
    }

    override suspend fun getTripById(tripId: String): Trip? {
        return container.tripCollection.findOne(TripCollection::id eq ObjectId(tripId))?.toEntity()
    }

    override suspend fun getTripByOrderId(orderId: String): Trip? {
        return container.tripCollection.findOne(TripCollection::orderId eq ObjectId(orderId))?.toEntity()
    }

    override suspend fun getAllTrips(page: Int, limit: Int): List<Trip> {
        return container.tripCollection.find()
            .paginate(page, limit).toList()
            .toEntity()
    }

    override suspend fun getActiveTripsByUserId(userId: String): List<Trip> {

        val pipeline = listOf(
            match(
                and(
                    TripCollection::clientId eq ObjectId(userId),
                    TripCollection::tripStatus ne Trip.Status.FINISHED.statusCode
                )
            ),
            lookup(
                from = "taxi",
                localField = "taxiId",
                foreignField = "_id",
                newAs = "taxi"
            ),
            unwind("\$taxi"),
            project(
                TripWithTaxi::id from "\$_id",
                TripWithTaxi::driverId from "\$driverId",
                TripWithTaxi::clientId from "\$clientId",
                TripWithTaxi::orderId from "\$orderId",
                TripWithTaxi::restaurantId from "\$restaurantId",
                TripWithTaxi::taxi from "\$taxi",
                TripWithTaxi::startPoint from "\$startPoint",
                TripWithTaxi::destination from "\$destination",
                TripWithTaxi::startPointAddress from "\$startPointAddress",
                TripWithTaxi::destinationAddress from "\$destinationAddress",
                TripWithTaxi::rate from "\$rate",
                TripWithTaxi::price from "\$price",
                TripWithTaxi::startDate from "\$startDate",
                TripWithTaxi::endDate from "\$endDate",
                TripWithTaxi::tripStatus from "\$tripStatus",
                TripWithTaxi::isATaxiTrip from "\$isATaxiTrip",
            )
        )

        return container.tripCollection.aggregate<TripWithTaxi>(pipeline).toList().map { it.toEntity() }
    }


    override suspend fun getDriverTripsHistory(
        driverId: String,
        page: Int,
        limit: Int
    ): List<Trip> {
        return container.tripCollection.find(
            and(TripCollection::driverId eq ObjectId(driverId))
        ).paginate(page, limit).toList().toEntity()
    }

    override suspend fun getClientTripsHistory(clientId: String, page: Int, limit: Int): List<Trip> {
        return container.tripCollection.aggregate<TripWithTaxi>(
            match(
                and(
                    TripCollection::clientId eq ObjectId(clientId),
                    TripCollection::endDate ne null
                )
            ),
            lookup(
                from = DataBaseContainer.TAXI_COLLECTION_NAME,
                localField = TripCollection::taxiId.name,
                foreignField = "_id",
                newAs = "taxi"
            ),
            unwind("\$taxi"),
            project(
                TripWithTaxi::id from "\$_id",
                TripWithTaxi::driverId from "\$driverId",
                TripWithTaxi::clientId from "\$clientId",
                TripWithTaxi::orderId from "\$orderId",
                TripWithTaxi::taxi from "\$taxi",
                TripWithTaxi::startPoint from "\$startPoint",
                TripWithTaxi::destination from "\$destination",
                TripWithTaxi::startPointAddress from "\$startPointAddress",
                TripWithTaxi::destinationAddress from "\$destinationAddress",
                TripWithTaxi::rate from "\$rate",
                TripWithTaxi::price from "\$price",
                TripWithTaxi::startDate from "\$startDate",
                TripWithTaxi::endDate from "\$endDate",
                TripWithTaxi::tripStatus from "\$tripStatus"
            ),
            skip((page - 1) * limit),
            limit(limit)
        ).toList().map { it.toEntity() }
    }

    override suspend fun getTripStatus(tripId: String): Int {
        val trip = container.tripCollection.findOne(TripCollection::id eq ObjectId(tripId))
            ?: throw ResourceNotFoundException()

        return trip.tripStatus
    }

    override suspend fun updateTrip(driverId: String, taxiId: String, tripId: String, statusCode: Int): Trip? {
        val updates = mutableListOf<Bson>()

        when (Trip.getOrderStatus(statusCode)) {
            Trip.Status.PENDING -> {
                updates.add(Updates.set(TripCollection::tripStatus.name, statusCode))
            }

            Trip.Status.APPROVED -> {
                updates.add(Updates.set(TripCollection::tripStatus.name, statusCode))
                updates.add(Updates.set(TripCollection::taxiId.name, ObjectId(taxiId)))
                updates.add(Updates.set(TripCollection::driverId.name, ObjectId(driverId)))
                updates.add(
                    Updates.set(
                        TripCollection::startDate.name,
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
                    )
                )
            }

            Trip.Status.RECEIVED -> {
                updates.add(Updates.set(TripCollection::tripStatus.name, statusCode))
            }

            Trip.Status.FINISHED -> {
                updates.add(Updates.set(TripCollection::tripStatus.name, statusCode))
                updates.add(
                    Updates.set(
                        TripCollection::endDate.name,
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
                    )
                )
            }
        }

        val combinedUpdates = Updates.combine(updates)

        container.tripCollection.updateOne(
            filter = and(TripCollection::id eq ObjectId(tripId)),
            update = combinedUpdates
        )

        val pipeline = listOf(
            match(TripCollection::id eq ObjectId(tripId)),
            lookup(
                from = "taxi",
                localField = "taxiId",
                foreignField = "_id",
                newAs = "taxi"
            ),
            unwind("\$taxi"),
            project(
                TripWithTaxi::id from "\$_id",
                TripWithTaxi::driverId from "\$driverId",
                TripWithTaxi::clientId from "\$clientId",
                TripWithTaxi::orderId from "\$orderId",
                TripWithTaxi::restaurantId from "\$restaurantId",
                TripWithTaxi::taxi from "\$taxi",
                TripWithTaxi::startPoint from "\$startPoint",
                TripWithTaxi::destination from "\$destination",
                TripWithTaxi::startPointAddress from "\$startPointAddress",
                TripWithTaxi::destinationAddress from "\$destinationAddress",
                TripWithTaxi::rate from "\$rate",
                TripWithTaxi::price from "\$price",
                TripWithTaxi::startDate from "\$startDate",
                TripWithTaxi::endDate from "\$endDate",
                TripWithTaxi::tripStatus from "\$tripStatus",
                TripWithTaxi::isATaxiTrip from "\$isATaxiTrip"
            )
        )

        val updatedTrip = container.tripCollection.aggregate<TripWithTaxi>(pipeline).first()

        return updatedTrip?.toEntity()
    }


    override suspend fun rateTrip(tripId: String, rate: Double): Trip? {
        return container.tripCollection.findOneAndUpdate(
            filter = and(TripCollection::id eq ObjectId(tripId)),
            update = Updates.set(TripCollection::rate.name, rate),
            options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        )?.toEntity()
    }

    override suspend fun getNumberOfTripsByDriverId(id: String): Long {
        return container.tripCollection.countDocuments(
            and(TripCollection::driverId eq ObjectId(id))
        )
    }

    override suspend fun getNumberOfTripsByClientId(id: String): Long {
        return container.tripCollection.countDocuments(
            and(
                TripCollection::clientId eq ObjectId(id),
                TripCollection::endDate ne null
            )
        )
    }

    override suspend fun deleteTaxiByDriverId(driverId: String): Boolean {
        return container.taxiCollection.updateMany(
            filter = TaxiCollection::driverId eq ObjectId(driverId),
            update = set(TaxiCollection::isDeleted setTo true),
        ).isSuccessfullyUpdated()
    }

    override suspend fun deleteAllCollections(): Boolean = runBlocking {
        val taxiCollection = container.taxiCollection
        val tripCollection = container.tripCollection

        try {
            taxiCollection.deleteMany(Filters.empty())
            tripCollection.deleteMany(Filters.empty())
            return@runBlocking true
        } catch (e: Exception) {
            e.printStackTrace()
            return@runBlocking false
        }
    }
    //endregion
}