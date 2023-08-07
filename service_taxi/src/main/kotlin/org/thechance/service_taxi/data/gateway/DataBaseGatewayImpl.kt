package org.thechance.service_taxi.data.gateway

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.and
import org.litote.kmongo.eq
import org.litote.kmongo.ne
import org.thechance.service_taxi.api.models.taxi.toCollection
import org.thechance.service_taxi.api.models.taxi.toEntity
import org.thechance.service_taxi.api.models.trip.toCollection
import org.thechance.service_taxi.api.models.trip.toEntity
import org.thechance.service_taxi.data.DataBaseContainer
import org.thechance.service_taxi.data.collection.TaxiCollection
import org.thechance.service_taxi.data.collection.TripCollection
import org.thechance.service_taxi.data.utils.isNotNull
import org.thechance.service_taxi.data.utils.isSuccessfullyUpdated
import org.thechance.service_taxi.data.utils.paginate
import org.thechance.service_taxi.data.utils.updateNotNullProperties
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.TaxiUpdateRequest
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.entity.TripUpdateRequest
import org.thechance.service_taxi.domain.gateway.DataBaseGateway

@Single
class DataBaseGatewayImpl(private val container: DataBaseContainer) : DataBaseGateway {
    // region taxi curd
    override suspend fun addTaxi(taxi: Taxi): Boolean {
        return container.taxiCollection.insertOne(taxi.toCollection()).wasAcknowledged()
    }

    override suspend fun getTaxiById(taxiId: String): Taxi? {
        return container.taxiCollection.findOneById(ObjectId(taxiId))
            ?.takeIf { !it.isDeleted }?.toEntity()
    }

    override suspend fun getAllTaxes(page: Int, limit: Int): List<Taxi> {
        return container.taxiCollection.find(TaxiCollection::isDeleted ne true)
            .paginate(page, limit).toList().toEntity()
    }

    override suspend fun deleteTaxi(taxiId: String): Boolean {
        return container.taxiCollection.updateOneById(
            id = ObjectId(taxiId),
            update = Updates.set(TaxiCollection::isDeleted.name, true),
            updateOnlyNotNullProperties = true
        ).isSuccessfullyUpdated()
    }

    override suspend fun updateTaxi(taxi: TaxiUpdateRequest): Boolean {
        return container.taxiCollection.findOneAndUpdate(
            filter = and(
                Filters.ne(TaxiCollection::isDeleted.name, true),
                Filters.ne(TaxiCollection::id.name, ObjectId(taxi.id)),
            ),
            update = updateNotNullProperties(
                taxi.toCollection(),
                otherFilter = { it != "isDeleted" && it != "id"})
        ).isNotNull()
    }
    //endregion

    //region trip curd
    override suspend fun addTrip(trip: Trip): Boolean {
        return container.tripCollection.insertOne(trip.toCollection()).wasAcknowledged()
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

    override suspend fun deleteTrip(tripId: String): Boolean {
        return container.tripCollection.updateOneById(
            id = ObjectId(tripId),
            update = Updates.set(TripCollection::isDeleted.name, true)
        ).isSuccessfullyUpdated()
    }

    override suspend fun updateTrip(trip: TripUpdateRequest): Boolean {
        return container.tripCollection.findOneAndUpdate(
            filter = and(
                Filters.ne(TripCollection::isDeleted.name, true),
                Filters.ne(TripCollection::id.name, ObjectId(trip.id)),
            ),
            update = updateNotNullProperties(
                trip.toCollection(),
                otherFilter = { it != "isDeleted" && it != "id"})
        ).isNotNull()
    }
    //endregion
}