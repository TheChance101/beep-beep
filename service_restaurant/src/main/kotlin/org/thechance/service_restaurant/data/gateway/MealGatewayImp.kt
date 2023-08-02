package org.thechance.service_restaurant.data.gateway

import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.updateOne
import org.litote.kmongo.eq
import org.litote.kmongo.set
import org.litote.kmongo.setTo
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.MealCollection
import org.thechance.service_restaurant.data.collection.mapper.toCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.data.utils.paginate
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

}