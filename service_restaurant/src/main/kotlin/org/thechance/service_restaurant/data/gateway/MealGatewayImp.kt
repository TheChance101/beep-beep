package org.thechance.service_restaurant.data.gateway

import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.litote.kmongo.coroutine.updateOne
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.MealCollection
import org.thechance.service_restaurant.data.collection.MealCuisinesCollection
import org.thechance.service_restaurant.data.collection.mapper.toCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.data.utils.paginate
import org.thechance.service_restaurant.entity.Cuisine
import org.thechance.service_restaurant.entity.Meal

@Single
class MealGatewayImp(private val container: DataBaseContainer) : MealGateway {

    private val mealCollection by lazy { container.database.getCollection<MealCollection>("meal") }

    override suspend fun addMeal(meal: Meal): Boolean = mealCollection.insertOne(meal.toCollection()).wasAcknowledged()

    override suspend fun getMeals(page: Int, limit: Int): List<Meal> =
        mealCollection.find(MealCollection::isDeleted eq false).paginate(page, limit).toList().toEntity()

    override suspend fun getMealById(id: String) = mealCollection.findOneById(ObjectId(id))?.toEntity()

    override suspend fun deleteMealById(id: String): Boolean =
        mealCollection.updateOne(
            filter = MealCollection::id eq ObjectId(id),
            update = set(MealCollection::isDeleted setTo true),
        ).wasAcknowledged()

    override suspend fun updateMeal(meal: Meal): Boolean =
        mealCollection.updateOne(meal.toCollection(), updateOnlyNotNullProperties = true)
            .wasAcknowledged()

    override suspend fun addCuisineToMeal(mealId: String, cuisineId: String): Boolean =
        mealCollection.updateOne(
            filter = MealCollection::id eq ObjectId(mealId),
            update = push(MealCollection::cuisines, ObjectId(cuisineId))
        ).wasAcknowledged()

    override suspend fun getMealCuisines(mealId: String): List<Cuisine> {
        return mealCollection.aggregate<MealCuisinesCollection>(
            match(
                and(
                    MealCollection::id eq ObjectId(mealId),
                    MealCollection::isDeleted ne true
                )
            ),
            lookup(
                localField = MealCollection::cuisines.name,
                from = "cuisine",
                foreignField = "_id",
                newAs = MealCuisinesCollection::meal_cuisines.name
            ),
        ).toList().firstOrNull()?.meal_cuisines?.filter { !it.isDeleted }?.toEntity() ?: emptyList()
    }

    override suspend fun deleteCuisineFromMeal(mealId: String, cuisineId: String): Boolean {
        return mealCollection.updateOne(
            MealCollection::id eq ObjectId(mealId),
            pull(MealCollection::cuisines, ObjectId(cuisineId)),
        ).wasAcknowledged()
    }

}