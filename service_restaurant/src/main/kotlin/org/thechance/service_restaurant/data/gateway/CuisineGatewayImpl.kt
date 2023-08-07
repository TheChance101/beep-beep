package org.thechance.service_restaurant.data.gateway

import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.*
import org.thechance.service_restaurant.data.collection.mapper.toCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.data.utils.isSuccessfullyUpdated
import org.thechance.service_restaurant.data.utils.paginate
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.gateway.CuisineGateway
import org.thechance.service_restaurant.data.Constants.MEAL_COLLECTION
import org.thechance.service_restaurant.data.collection.relationModels.MealCuisines


@Single
class CuisineGatewayImpl(private val container: DataBaseContainer) : CuisineGateway {

    override suspend fun getCuisines(page: Int, limit: Int): List<Cuisine> =
        container.cuisineCollection.find(MealCollection::isDeleted eq false).paginate(page, limit).toList().toEntity()

    override suspend fun getCuisineById(id: String): Cuisine? =
        container.cuisineCollection.findOneById(ObjectId(id))?.takeIf { !it.isDeleted }?.toEntity()

    override suspend fun getMealsInCuisine(cuisineId: String): List<Meal> {
        return container.cuisineCollection.aggregate<MealCuisines>(
            match(CuisineCollection::id eq ObjectId(cuisineId)),
            lookup(
                from = MEAL_COLLECTION,
                localField = CuisineCollection::meals.name,
                foreignField = "_id",
                newAs = MealCuisines::meals.name
            )
        ).toList().first().meals.filterNot { it.isDeleted }.toEntity()
    }


    override suspend fun addCuisine(cuisine: Cuisine): Boolean {
        return container.cuisineCollection.insertOne(cuisine.toCollection()).wasAcknowledged()
    }

    override suspend fun updateCuisine(cuisine: Cuisine): Boolean {
        return container.cuisineCollection.updateOneById(
            ObjectId(cuisine.id),
            cuisine.toCollection(),
            updateOnlyNotNullProperties = true
        ).isSuccessfullyUpdated()
    }

    override suspend fun deleteCuisine(id: String): Boolean =
        container.cuisineCollection.updateOne(
            filter = CuisineCollection::id eq ObjectId(id),
            update = set(CuisineCollection::isDeleted setTo true),
        ).wasAcknowledged()

}