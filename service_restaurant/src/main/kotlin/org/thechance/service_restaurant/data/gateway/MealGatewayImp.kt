package org.thechance.service_restaurant.data.gateway

import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.eq
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.MealCollection
import org.thechance.service_restaurant.data.collection.mapper.toCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.entity.Meal
import org.thechance.service_restaurant.usecase.gateway.MealGateway

@Single
class MealGatewayImp(private val container: DataBaseContainer) : MealGateway {

    private val mealCollection by lazy { container.database.getCollection<MealCollection>("meal") }
    override suspend fun addMeal(meal: Meal): Boolean = mealCollection.insertOne(meal.toCollection()).wasAcknowledged()

    override suspend fun getMeals(page: Int, limit: Int): List<Meal> =
        mealCollection.find(MealCollection::isDeleted eq false).skip((page - 1) * limit).limit(limit).toList()
            .toEntity()

    override suspend fun getMealById(id: String) = mealCollection.findOneById(ObjectId(id))?.toEntity()

    override suspend fun deleteMealById(id: String): Boolean =
        mealCollection.updateOneById(
            id = ObjectId(id),
            update = MealCollection(isDeleted = true),
            updateOnlyNotNullProperties = true
        ).wasAcknowledged()

    override suspend fun updateMeal(id: String, meal: Meal): Boolean =
        mealCollection.updateOneById(ObjectId(id), meal.toCollection(), updateOnlyNotNullProperties = true)
            .wasAcknowledged()


}