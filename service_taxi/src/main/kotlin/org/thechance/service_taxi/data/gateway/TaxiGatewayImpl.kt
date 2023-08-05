package org.thechance.service_taxi.data.gateway

import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.ne
import org.thechance.service_taxi.api.models.taxi.toCollection
import org.thechance.service_taxi.api.models.taxi.toEntity
import org.thechance.service_taxi.data.DataBaseContainer
import org.thechance.service_taxi.data.collection.TaxiCollection
import org.thechance.service_taxi.data.utils.paginate
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.gateway.TaxiGateway

@Single
class TaxiGatewayImpl(private val container: DataBaseContainer) : TaxiGateway {

    override suspend fun addTaxi(taxi: Taxi): Boolean {
        return container.taxiCollection.insertOne(taxi.toCollection()).wasAcknowledged()
    }

    override suspend fun getTaxiById(taxiId: String): Taxi? {
        return container.taxiCollection.findOneById(ObjectId(taxiId))?.takeIf { it.isDeleted != true }?.toEntity()
    }

    override suspend fun getAllTaxes(page: Int, limit: Int): List<Taxi> {
        return container.taxiCollection.find(TaxiCollection::isDeleted ne true)
            .paginate(page, limit).toList().toEntity()
    }

    override suspend fun deleteTaxi(taxiId: String): Boolean {
        return container.taxiCollection.updateOneById(
            id = ObjectId(taxiId),
            update = TaxiCollection(isDeleted = true),
            updateOnlyNotNullProperties = true
        ).modifiedCount > 0
    }

    override suspend fun updateTaxi(taxi: Taxi): Boolean {
        return container.taxiCollection.updateOneById(
            ObjectId(taxi.id),
            taxi.toCollection(),
            updateOnlyNotNullProperties = true
        ).modifiedCount > 0
    }

}