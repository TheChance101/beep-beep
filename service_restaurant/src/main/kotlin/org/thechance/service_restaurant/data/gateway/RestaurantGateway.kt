package org.thechance.service_restaurant.data.gateway

import com.mongodb.client.model.Accumulators
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import com.mongodb.client.model.Updates
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.*
import org.thechance.service_restaurant.data.collection.mapper.toCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.data.collection.relationModels.MealCuisines
import org.thechance.service_restaurant.data.collection.relationModels.MealWithCuisines
import org.thechance.service_restaurant.data.collection.relationModels.RestaurantCuisine
import org.thechance.service_restaurant.data.utils.getNonEmptyFieldsMap
import org.thechance.service_restaurant.data.utils.isSuccessfullyUpdated
import org.thechance.service_restaurant.data.utils.paginate
import org.thechance.service_restaurant.data.utils.toUUIDs
import org.thechance.service_restaurant.domain.entity.*
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.utils.exceptions.ERROR_ADD
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND
import java.util.*

class RestaurantGateway(private val container: DataBaseContainer) : IRestaurantGateway {

    //region restaurant permission request
    override suspend fun getRestaurantPermissionRequests(): List<RestaurantPermissionRequest> {
        return container.restaurantPermissionRequestCollection.find(
            RestaurantPermissionRequestCollection::isDeleted ne true
        ).toList().toEntity()
    }

    override suspend fun createRestaurantPermissionRequest(
        restaurantName: String,
        ownerEmail: String,
        cause: String
    ): RestaurantPermissionRequest {
        val addedRequest = RestaurantPermissionRequestCollection(
            restaurantName = restaurantName,
            ownerEmail = ownerEmail,
            cause = cause
        )
        container.restaurantPermissionRequestCollection.insertOne(addedRequest)
        return addedRequest.toEntity()
    }
    //endregion

    //region Restaurant
    override suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant> {
        return container.restaurantCollection.find(RestaurantCollection::isDeleted eq false)
            .paginate(page, limit).toList().toEntity()
    }

    override suspend fun getRestaurantsByOwnerId(ownerId: String): List<Restaurant> {
        return container.restaurantCollection.aggregate<RestaurantCollection>(
            match(
                and(
                    RestaurantCollection::ownerId eq UUID.fromString(ownerId),
                    RestaurantCollection::isDeleted eq false
                )
            ),
        ).toList().toEntity()
    }

    override suspend fun getRestaurant(id: String): Restaurant? {
        return container.restaurantCollection.aggregate<RestaurantCollection>(
            match(
                and(
                    RestaurantCollection::id eq UUID.fromString(id),
                    RestaurantCollection::isDeleted eq false
                )
            ),
        ).toList().firstOrNull()?.toEntity()
    }

    override suspend fun getRestaurantIds(): List<String> {
        return container.restaurantCollection.find(RestaurantCollection::isDeleted eq false)
            .toList().toEntity()
            .map { it.id }
    }

    override suspend fun getCuisineInRestaurant(restaurantId: String): List<Cuisine> {
        return container.restaurantCollection.aggregate<RestaurantCuisine>(
            match(RestaurantCollection::id eq UUID.fromString(restaurantId)),
            lookup(
                from = DataBaseContainer.CUISINE_COLLECTION,
                localField = RestaurantCollection::cuisineIds.name,
                foreignField = "_id",
                newAs = RestaurantCuisine::cuisines.name
            )
        ).toList().first().cuisines.filterNot { it.isDeleted }.toEntity()
    }

    override suspend fun addRestaurant(restaurant: Restaurant): Restaurant {
        val addedRestaurant = restaurant.toCollection()
        container.restaurantCollection.insertOne(addedRestaurant)
        return addedRestaurant.toEntity()
    }

    override suspend fun addCuisineToRestaurant(
        restaurantId: String,
        cuisineIds: List<String>
    ): Boolean {
        return container.restaurantCollection.updateOneById(
            UUID.fromString(restaurantId),
            update = Updates.addEachToSet(
                RestaurantCollection::cuisineIds.name,
                cuisineIds.toUUIDs()
            )
        ).isSuccessfullyUpdated()
    }

    override suspend fun addMealToRestaurant(restaurantId: String, mealId: String): Boolean {
        return container.restaurantCollection.updateOneById(
            UUID.fromString(restaurantId),
            update = Updates.addToSet(RestaurantCollection::mealIds.name, UUID.fromString(mealId))
        ).isSuccessfullyUpdated()
    }

    override suspend fun updateRestaurant(restaurant: Restaurant): Restaurant {
        val fieldsToUpdate = getNonEmptyFieldsMap(restaurant.copy(id = "", ownerId = ""))
        if (restaurant.location.latitude != -1.0 && restaurant.location.longitude != -1.0) {
            val addressUpdateFields = getNonEmptyFieldsMap(restaurant.location)
            fieldsToUpdate[RestaurantCollection::location.name] = addressUpdateFields
        }
        return container.restaurantCollection.findOneAndUpdate(
            filter = RestaurantCollection::id eq UUID.fromString(restaurant.id),
            update = Updates.combine(fieldsToUpdate.map { Updates.set(it.key, it.value) }),
            options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        )?.toEntity() ?: throw MultiErrorException(listOf(NOT_FOUND))
    }


    override suspend fun deleteRestaurant(restaurantId: String): Boolean {
        return container.restaurantCollection.updateOneById(
            id = UUID.fromString(restaurantId),
            update = Updates.set(RestaurantCollection::isDeleted.name, true),
        ).isSuccessfullyUpdated()
    }

    override suspend fun deleteCategoriesInRestaurant(
        restaurantId: String,
        categoryIds: List<String>
    ): Boolean {
        val resultDeleteFromCategory = container.categoryCollection.updateMany(
            CategoryCollection::id `in` categoryIds.toUUIDs(),
            pull(CategoryCollection::restaurantIds, UUID.fromString(restaurantId))
        ).isSuccessfullyUpdated()

        val resultDeleteFromRestaurant = container.restaurantCollection.updateOneById(
            UUID.fromString(restaurantId),
            pullAll(RestaurantCollection::categoryIds, categoryIds.toUUIDs())
        ).isSuccessfullyUpdated()
        return resultDeleteFromRestaurant and resultDeleteFromCategory
    }

    //need to delete in both collection
    override suspend fun deleteCuisinesInRestaurant(
        restaurantId: String,
        cuisineIds: List<String>
    ): Boolean {
        return container.restaurantCollection.updateOneById(
            UUID.fromString(restaurantId),
            pullAll(RestaurantCollection::cuisineIds, cuisineIds.toUUIDs())
        ).isSuccessfullyUpdated()
    }

    override suspend fun getCuisinesNotInRestaurant(
        restaurantId: String,
        cuisineIds: List<String>
    ): List<String> {
        val deletedCuisineIds = container.mealCollection.aggregate<MealCollection>(
            match(
                and(
                    MealCollection::restaurantId eq UUID.fromString(restaurantId),
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
        return container.mealCollection.find(MealCollection::isDeleted eq false)
            .paginate(page, limit).toList()
            .toEntity()
    }

    override suspend fun getMealById(id: String): MealDetails? {
        return container.mealCollection.aggregate<MealWithCuisines>(
            match(MealCollection::id eq UUID.fromString(id)),
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
                    MealCollection::id eq UUID.fromString(mealId),
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

    override suspend fun addMeal(meal: MealDetails): Meal {
        val mealDocument = meal.toCollection()
        val addedMeal = container.mealCollection.insertOne(mealDocument).wasAcknowledged()
        val addedMealToCuisine = container.cuisineCollection.updateMany(
            CuisineCollection::id `in` meal.cuisines.map { it.id }.toUUIDs(),
            addToSet(CuisineCollection::meals, mealDocument.id)
        ).isSuccessfullyUpdated()

        return if (addedMeal && addedMealToCuisine) {
            mealDocument.toEntity()
        } else {
            throw MultiErrorException(listOf(ERROR_ADD))
        }
    }

    override suspend fun addCuisinesToMeal(mealId: String, cuisineIds: List<String>): Boolean {
        val resultAddToCuisine = container.cuisineCollection.updateMany(
            CuisineCollection::id `in` cuisineIds.toUUIDs(),
            addToSet(CuisineCollection::meals, UUID.fromString(mealId))
        ).isSuccessfullyUpdated()

        val resultAddToMeal = container.mealCollection.updateOneById(
            UUID.fromString(mealId),
            update = Updates.addEachToSet(MealCollection::cuisines.name, cuisineIds.toUUIDs())
        ).isSuccessfullyUpdated()

        return resultAddToCuisine and resultAddToMeal
    }

    override suspend fun updateMeal(meal: MealDetails): Meal {
        val fieldsToUpdate =
            getNonEmptyFieldsMap(meal.copy(id = "", restaurantId = "", cuisines = emptyList()))
        if (meal.cuisines.isNotEmpty()) {
            fieldsToUpdate[MealDetails::cuisines.name] = meal.cuisines.map { UUID.fromString(it.id) }
        }
        return container.mealCollection.findOneAndUpdate(
            filter = MealCollection::id eq UUID.fromString(meal.id),
            update = Updates.combine(fieldsToUpdate.map { Updates.set(it.key, it.value) }),
            options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        )?.toEntity() ?: throw MultiErrorException(listOf(NOT_FOUND))

    }


    override suspend fun deleteMealById(id: String): Boolean =
        container.mealCollection.updateOne(
            filter = MealCollection::id eq UUID.fromString(id),
            update = set(MealCollection::isDeleted setTo true),
        ).wasAcknowledged()

    override suspend fun deleteCuisineFromMeal(mealId: String, cuisineId: String): Boolean {
        return container.mealCollection.updateOne(
            MealCollection::id eq UUID.fromString(mealId),
            pull(MealCollection::cuisines, UUID.fromString(cuisineId)),
        ).wasAcknowledged()
    }
    //endregion
}