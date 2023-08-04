package org.thechance.service_restaurant.data.gateway

import com.mongodb.client.model.Updates
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.litote.kmongo.coroutine.updateOne
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.*
import org.thechance.service_restaurant.data.collection.mapper.toCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.data.utils.isSuccessfullyUpdated
import org.thechance.service_restaurant.data.utils.paginate
import org.thechance.service_restaurant.data.utils.toObjectIds
import org.thechance.service_restaurant.entity.Cuisine
import org.thechance.service_restaurant.entity.Meal
import org.thechance.service_restaurant.utils.Constants
import org.thechance.service_restaurant.utils.Constants.CUISINE_COLLECTION
import org.thechance.service_restaurant.utils.Constants.MEAL_COLLECTION


@Single
class CuisineGatewayImpl(private val container: DataBaseContainer) : CuisineGateway {

    override suspend fun getCuisines(page: Int, limit: Int): List<Cuisine> =
        container.cuisineCollection.find(MealCollection::isDeleted eq false).paginate(page, limit).toList().toEntity()

    override suspend fun getCuisineById(id: String): Cuisine? =
        container.cuisineCollection.findOneById(ObjectId(id))?.toEntity()

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

    override suspend fun addMealsToCuisine(cuisineId: String, mealIds: List<String>): Boolean {
        val resultAddToMeals = container.mealCollection.updateMany(
            MealCollection::id `in` mealIds.toObjectIds(),
            addToSet(MealCollection::cuisines, ObjectId(cuisineId))
        ).isSuccessfullyUpdated()

        val resultAddToCuisine = container.cuisineCollection.updateOneById(
            ObjectId(cuisineId),
            update = Updates.addEachToSet(CuisineCollection::meals.name, mealIds.toObjectIds())
        ).isSuccessfullyUpdated()
        return resultAddToMeals and resultAddToCuisine
    }

    override suspend fun updateCuisine(cuisine: Cuisine): Boolean {
        return container.cuisineCollection.updateOne(
            cuisine.toCollection(), updateOnlyNotNullProperties = true
        ).wasAcknowledged()
    }

    override suspend fun deleteCuisine(id: String): Boolean =
        container.cuisineCollection.updateOne(
            filter = CuisineCollection::id eq ObjectId(id),
            update = set(CuisineCollection::isDeleted setTo true),
        ).wasAcknowledged()

    override suspend fun deleteMealsInCuisine(cuisineId: String, mealIds: List<String>): Boolean {
        TODO("Not yet implemented")
    }

}