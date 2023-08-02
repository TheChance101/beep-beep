package org.thechance.service_taxi.data

import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.eq
import org.thechance.service_taxi.api.models.toCollection
import org.thechance.service_taxi.api.models.toTaxes
import org.thechance.service_taxi.api.models.toTaxi
import org.thechance.service_taxi.data.utils.paginate
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.gateway.TaxiGateway

@Single
class TaxiGatewayImpl(container: DataBaseContainer) : TaxiGateway {
    private val collection by lazy { container.database.getCollection<TaxiCollection>("taxi") }

    override suspend fun addTaxi(taxi: Taxi): Boolean {
        return collection.insertOne(taxi.toCollection()).wasAcknowledged()
    }

    override suspend fun getTaxiById(taxiId: String): Taxi? {
        return collection.findOneById(ObjectId(taxiId))?.takeIf { it.isDeleted != true }?.toTaxi()
    }

    override suspend fun getAllTaxes(page: Int, limit: Int): List<Taxi> {
        return collection.find(TaxiCollection::isDeleted eq false)
            .paginate(page, limit).toList().toTaxes()
    }

    override suspend fun deleteTaxi(taxiId: String): Boolean {
        return collection.updateOneById(
            id = ObjectId(taxiId),
            update = TaxiCollection(isDeleted = true),
            updateOnlyNotNullProperties = true
        ).wasAcknowledged()
    }

    override suspend fun updateTaxi(taxi: Taxi): Boolean {
        return collection.updateOneById(
            ObjectId(taxi.id),
            taxi.toCollection(),
            updateOnlyNotNullProperties = true
        ).wasAcknowledged()
    }

}