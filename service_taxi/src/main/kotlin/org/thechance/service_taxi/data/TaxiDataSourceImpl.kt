package org.thechance.service_taxi.data

import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.eq
import org.litote.kmongo.setTo
import org.thechance.service_taxi.domain.datasource.TaxiDataSource
import org.thechance.service_taxi.domain.entity.Taxi

@Single
class TaxiDataSourceImpl(container: DataBaseContainer) : TaxiDataSource {
    private val collection by lazy { container.db.getCollection<Taxi>("taxi") }

    override suspend fun addTaxi(taxi: Taxi): Boolean {
        return collection.insertOne(taxi).wasAcknowledged()
    }

    override suspend fun getTaxiById(taxiId: String): Taxi? {
        return collection.findOneById(Taxi::id eq ObjectId(taxiId))?.takeIf { !it.deleted }
    }

    override suspend fun getAllTaxes(): List<Taxi> {
        return collection.find(Taxi::deleted eq false).toList()
    }

    override suspend fun deleteTaxi(taxiId: String): Boolean {
        return collection.updateOneById(taxiId, Taxi::deleted setTo true).wasAcknowledged()
    }

    override suspend fun updateTaxi(taxiId: String, taxi: Taxi): Boolean {
        return collection.updateOneById(taxiId, taxi, updateOnlyNotNullProperties = true)
            .wasAcknowledged()
    }

}