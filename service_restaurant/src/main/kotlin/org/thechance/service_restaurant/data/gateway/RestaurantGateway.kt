package org.thechance.service_restaurant.data.gateway

import com.mongodb.client.model.Accumulators
import com.mongodb.client.model.Updates
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.CategoryCollection
import org.thechance.service_restaurant.data.collection.CuisineCollection
import org.thechance.service_restaurant.data.collection.MealCollection
import org.thechance.service_restaurant.data.collection.RestaurantCollection
import org.thechance.service_restaurant.data.collection.mapper.toCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.data.collection.relationModels.MealCuisines
import org.thechance.service_restaurant.data.collection.relationModels.MealWithCuisines
import org.thechance.service_restaurant.data.collection.relationModels.RestaurantCuisine
import org.thechance.service_restaurant.data.utils.getNonEmptyFieldsMap
import org.thechance.service_restaurant.data.utils.isSuccessfullyUpdated
import org.thechance.service_restaurant.data.utils.paginate
import org.thechance.service_restaurant.data.utils.toObjectIds
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.MealDetails
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway

@Single
class RestaurantGateway(private val container: DataBaseContainer) : IRestaurantGateway {

    //region Restaurant
    override suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant> {
        return container.restaurantCollection.find(RestaurantCollection::isDeleted eq false)
            .paginate(page, limit).toList().toEntity()
    }

    override suspend fun getRestaurant(id: String): Restaurant? {
        return container.restaurantCollection.aggregate<RestaurantCollection>(
            match(
                and(
                    RestaurantCollection::id eq ObjectId(id),
                    RestaurantCollection::isDeleted eq false
                )
            ),
        ).toList().firstOrNull()?.toEntity()
    }

    override suspend fun getRestaurantIds(): List<String> {
        return container.restaurantCollection.find(RestaurantCollection::isDeleted eq false).toList().toEntity()
            .map { it.id }
    }

    override suspend fun getCuisineInRestaurant(restaurantId: String): List<Cuisine> {
        return container.restaurantCollection.aggregate<RestaurantCuisine>(
            match(RestaurantCollection::id eq ObjectId(restaurantId)),
            lookup(
                from = DataBaseContainer.CUISINE_COLLECTION,
                localField = RestaurantCollection::cuisineIds.name,
                foreignField = "_id",
                newAs = RestaurantCuisine::cuisines.name
            )
        ).toList().first().cuisines.filterNot { it.isDeleted }.toEntity()
    }

    override suspend fun addRestaurant(restaurant: Restaurant): Boolean {
        return container.restaurantCollection.insertOne(restaurant.toCollection()).wasAcknowledged()
    }

    override suspend fun addCuisineToRestaurant(restaurantId: String, cuisineIds: List<String>): Boolean {
        return container.restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            update = Updates.addEachToSet(RestaurantCollection::cuisineIds.name, cuisineIds.toObjectIds())
        ).isSuccessfullyUpdated()
    }

    override suspend fun addMealToRestaurant(restaurantId: String, mealId: String): Boolean {
        return container.restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            update = Updates.addToSet(RestaurantCollection::mealIds.name, ObjectId(mealId))
        ).isSuccessfullyUpdated()
    }

    override suspend fun updateRestaurant(restaurant: Restaurant): Boolean {
        val fieldsToUpdate = getNonEmptyFieldsMap(restaurant.copy(id = "", ownerId = ""))
        if (restaurant.address.latitude != -1.0 && restaurant.address.longitude != -1.0) {
            val addressUpdateFields = getNonEmptyFieldsMap(restaurant.address)
            fieldsToUpdate[RestaurantCollection::address.name] = addressUpdateFields
        }
        return container.restaurantCollection.updateOneById(
            id = ObjectId(restaurant.id),
            update = fieldsToUpdate,
            updateOnlyNotNullProperties = true
        ).isSuccessfullyUpdated()
    }


    override suspend fun deleteRestaurant(restaurantId: String): Boolean {
        return container.restaurantCollection.updateOneById(
            id = ObjectId(restaurantId),
            update = Updates.set(RestaurantCollection::isDeleted.name, true),
        ).isSuccessfullyUpdated()
    }

    override suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        val resultDeleteFromCategory = container.categoryCollection.updateMany(
            CategoryCollection::id `in` categoryIds.toObjectIds(),
            pull(CategoryCollection::restaurantIds, ObjectId(restaurantId))
        ).isSuccessfullyUpdated()

        val resultDeleteFromRestaurant = container.restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            pullAll(RestaurantCollection::categoryIds, categoryIds.toObjectIds())
        ).isSuccessfullyUpdated()
        return resultDeleteFromRestaurant and resultDeleteFromCategory
    }

    //need to delete in both collection
    override suspend fun deleteCuisinesInRestaurant(restaurantId: String, cuisineIds: List<String>): Boolean {
        return container.restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            pullAll(RestaurantCollection::cuisineIds, cuisineIds.toObjectIds())
        ).isSuccessfullyUpdated()
    }

    override suspend fun getCuisinesNotInRestaurant(restaurantId: String, cuisineIds: List<String>): List<String> {
        val deletedCuisineIds = container.mealCollection.aggregate<MealCollection>(
            match(
                and(
                    MealCollection::restaurantId eq ObjectId(restaurantId),
                    MealCollection::isDeleted eq false
                )
            ),
            lookup(
                from = DataBaseContainer.RESTAURANT_COLLECTION,
                localField = MealCollection::restaurantId.name,
                foreignField = "_id",
                newAs = MealCollection::cuisines.name
            ),
            unwind(MealCollection::cuisines.name),
            group(MealCollection::cuisines, Accumulators.addToSet("_id", "\$_id")),
        ).toList().flatMap { it.cuisines }.filter { it.toString() in cuisineIds }
        return deletedCuisineIds.map { it.toString() }
    }
    //endregion

    //region meal
    override suspend fun getMeals(page: Int, limit: Int): List<Meal> {
        return container.mealCollection.find(MealCollection::isDeleted eq false).paginate(page, limit).toList()
            .toEntity()
    }

    override suspend fun getMealById(id: String): MealDetails? {
        return container.mealCollection.aggregate<MealWithCuisines>(
            match(MealCollection::id eq ObjectId(id)),
            lookup(
                from = DataBaseContainer.CUISINE_COLLECTION,
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
                from = DataBaseContainer.CUISINE_COLLECTION,
                localField = MealCollection::cuisines.name,
                foreignField = "_id",
                newAs = MealCuisines::cuisines.name
            )
        ).toList().first().cuisines.filterNot { it.isDeleted }.toEntity()
    }

    override suspend fun addMeal(meal: MealDetails): Boolean {
        val mealDocument = meal.toCollection()
        val addedMeal = container.mealCollection.insertOne(mealDocument).wasAcknowledged()

        val addedMealToCuisine = container.cuisineCollection.updateMany(
            CuisineCollection::id `in` meal.cuisines.map { it.id }.toObjectIds(),
            addToSet(CuisineCollection::meals, mealDocument.id)
        ).isSuccessfullyUpdated()

        return addedMealToCuisine and addedMeal
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
        val fieldsToUpdate = getNonEmptyFieldsMap(meal.copy(id = "", restaurantId = "", cuisines = emptyList()))
        if (meal.cuisines.isNotEmpty()) {
            fieldsToUpdate[MealDetails::cuisines.name] = meal.cuisines.map { ObjectId(it.id) }
        }
        return container.mealCollection.updateOneById(
            ObjectId(meal.id),
            fieldsToUpdate,
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
    //endregion
}