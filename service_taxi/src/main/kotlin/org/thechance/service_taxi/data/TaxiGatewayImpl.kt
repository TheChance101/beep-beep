package org.thechance.service_taxi.data

import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.eq
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.gateway.TaxiGateway

@Single
class TaxiGatewayImpl(container: DataBaseContainer) : TaxiGateway {
    private val collection by lazy { container.db.getCollection<TaxiCollection>("taxi") }

    override suspend fun addTaxi(taxi: Taxi): Boolean {
        return collection.insertOne(taxi.toCollection()).wasAcknowledged()
    }

    override suspend fun getTaxiById(taxiId: String): Taxi? {
        return collection.findOneById(ObjectId(taxiId))?.takeIf { it.isDeleted != true }?.toTaxi()
    }

    override suspend fun getAllTaxes(): List<Taxi> {
        return collection.find(TaxiCollection::isDeleted eq false).toList().toTaxes()
    }

    override suspend fun deleteTaxi(taxiId: String): Boolean {
        return collection.updateOneById(
            id = ObjectId(taxiId),
            update = TaxiCollection(isDeleted = true),
            updateOnlyNotNullProperties = true
        ).wasAcknowledged()
    }

    override suspend fun updateTaxi(taxiId: String, taxi: Taxi): Boolean {
        return collection.updateOneById(
            ObjectId(taxiId),
            taxi.toCollection(),
            updateOnlyNotNullProperties = true
        ).wasAcknowledged()
    }

}