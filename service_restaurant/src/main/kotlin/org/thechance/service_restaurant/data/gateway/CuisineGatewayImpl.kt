package org.thechance.service_restaurant.data.gateway

import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.updateOne
import org.litote.kmongo.eq
import org.litote.kmongo.set
import org.litote.kmongo.setTo
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.CuisineCollection
import org.thechance.service_restaurant.data.collection.MealCollection
import org.thechance.service_restaurant.data.collection.mapper.toCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.data.utils.paginate
import org.thechance.service_restaurant.entity.Cuisine
import org.thechance.service_restaurant.usecase.gateway.CuisineGateway


@Single
class CuisineGatewayImpl(private val container: DataBaseContainer) : CuisineGateway {

    private val cuisineCollection by lazy { container.database.getCollection<CuisineCollection>("cuisine") }

    override suspend fun addCuisine(cuisine: Cuisine): Boolean {
        return cuisineCollection.insertOne(cuisine.toCollection()).wasAcknowledged()
    }

    override suspend fun deleteCuisine(id: String): Boolean =
        cuisineCollection.updateOne(
            filter = CuisineCollection::id eq ObjectId(id),
            update = set(CuisineCollection::isDeleted setTo true),
        ).wasAcknowledged()


    override suspend fun getCuisines(page: Int, limit: Int): List<Cuisine> =
        cuisineCollection.find(MealCollection::isDeleted eq false).paginate(page, limit).toList().toEntity()

    override suspend fun getCuisineById(id: String): Cuisine? = cuisineCollection.findOneById(ObjectId(id))?.toEntity()

    override suspend fun updateCuisine(cuisine: Cuisine): Boolean {
        return cuisineCollection.updateOne(
            cuisine.toCollection(), updateOnlyNotNullProperties = true
        ).wasAcknowledged()
    }

}