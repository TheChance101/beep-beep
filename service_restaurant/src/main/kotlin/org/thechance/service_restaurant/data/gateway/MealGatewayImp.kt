package org.thechance.service_restaurant.data.gateway

import com.mongodb.client.model.Updates
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.thechance.service_restaurant.data.Constants.CUISINE_COLLECTION
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.CuisineCollection
import org.thechance.service_restaurant.data.collection.MealCollection
import org.thechance.service_restaurant.data.collection.MealCuisines
import org.thechance.service_restaurant.data.collection.MealWithCuisines
import org.thechance.service_restaurant.data.collection.mapper.toCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.data.utils.getNonEmptyFieldsMap
import org.thechance.service_restaurant.data.utils.isSuccessfullyUpdated
import org.thechance.service_restaurant.data.utils.paginate
import org.thechance.service_restaurant.data.utils.toObjectIds
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.MealDetails
import org.thechance.service_restaurant.domain.gateway.MealGateway

@Single
class MealGatewayImp(private val container: DataBaseContainer) : MealGateway {

    override suspend fun getMeals(page: Int, limit: Int): List<Meal> {
        return container.mealCollection.find(MealCollection::isDeleted eq false).paginate(page, limit).toList()
            .toEntity()
    }

    override suspend fun getMealById(id: String): MealDetails? {
        return container.mealCollection.aggregate<MealWithCuisines>(
            match(MealCollection::id eq ObjectId(id)),
            lookup(
                from = CUISINE_COLLECTION,
                localField = MealCollection::cuisines.name,
                foreignField = "_id",
                newAs = MealWithCuisines::cuisines.name
            )

        ).toList().firstOrNull()?.toEntity()
    }

    override suspend fun getMealCuisines(mealId: String): List<Cuisine> {
        return container.mealCollection.aggregate<MealCuisines>(
            match(
                and(
                    MealCollection::id eq ObjectId(mealId),
                    MealCollection::isDeleted eq false
                )
            ),
            lookup(
                from = CUISINE_COLLECTION,
                localField = MealCollection::cuisines.name,
                foreignField = "_id",
                newAs = MealCuisines::cuisines.name
            )
        ).toList().first().cuisines.filterNot { it.isDeleted }.toEntity()
    }

    override suspend fun addMeal(meal: MealDetails): Boolean {
        return container.mealCollection.insertOne(meal.toCollection()).wasAcknowledged()
    }

    override suspend fun addCuisinesToMeal(mealId: String, cuisineIds: List<String>): Boolean {
        val resultAddToCuisine = container.cuisineCollection.updateMany(
            CuisineCollection::id `in` cuisineIds.toObjectIds(),
            addToSet(CuisineCollection::meals, ObjectId(mealId))
        ).isSuccessfullyUpdated()

        val resultAddToMeal = container.mealCollection.updateOneById(
            ObjectId(mealId),
            update = Updates.addEachToSet(MealCollection::cuisines.name, cuisineIds.toObjectIds())
        ).isSuccessfullyUpdated()

        return resultAddToCuisine and resultAddToMeal
    }

    override suspend fun updateMeal(meal: MealDetails): Boolean {
        val updateFields = getNonEmptyFieldsMap(meal.copy(id = "", restaurantId = "", cuisines = emptyList()))
        if (meal.cuisines.isNotEmpty()) {
            updateFields[MealDetails::cuisines.name] = meal.cuisines.map { ObjectId(it.id) }
        }
        return container.mealCollection.updateOneById(
            ObjectId(meal.id),
            updateFields,
            updateOnlyNotNullProperties = true
        ).isSuccessfullyUpdated()
    }


    override suspend fun deleteMealById(id: String): Boolean =
        container.mealCollection.updateOne(
            filter = MealCollection::id eq ObjectId(id),
            update = set(MealCollection::isDeleted setTo true),
        ).wasAcknowledged()

    override suspend fun deleteCuisineFromMeal(mealId: String, cuisineId: String): Boolean {
        return container.mealCollection.updateOne(
            MealCollection::id eq ObjectId(mealId),
            pull(MealCollection::cuisines, ObjectId(cuisineId)),
        ).wasAcknowledged()
    }

}