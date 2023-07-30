package org.thechance.service_taxi.data

import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.eq
import org.litote.kmongo.setTo
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.gateway.TaxiGateway

@Single
class TaxiGatewayImpl(container: DataBaseContainer) : TaxiGateway {
    private val collection by lazy { container.db.getCollection<TaxiCollection>("taxi") }

    override suspend fun addTaxi(taxi: Taxi): Boolean {
        return collection.insertOne(taxi.toCollection()).wasAcknowledged()
    }

    override suspend fun getTaxiById(taxiId: String): Taxi? {
        return collection.findOneById(TaxiCollection::id eq ObjectId(taxiId))
            ?.takeIf { !it.isDeleted }?.toTaxi()
    }

    override suspend fun getAllTaxes(): List<Taxi> {
        return collection.find(TaxiCollection::isDeleted eq false).toList().toTaxes()
    }

    override suspend fun deleteTaxi(taxiId: String): Boolean {
        return collection.updateOneById(taxiId, TaxiCollection::isDeleted setTo true)
            .wasAcknowledged()
    }

    override suspend fun updateTaxi(taxiId: String, taxi: Taxi): Boolean {
        return collection.updateOneById(taxiId, taxi, updateOnlyNotNullProperties = true)
            .wasAcknowledged()
    }

}