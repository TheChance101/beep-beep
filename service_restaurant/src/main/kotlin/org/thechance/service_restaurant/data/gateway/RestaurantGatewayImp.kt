package org.thechance.service_restaurant.data.gateway

import com.mongodb.client.model.Accumulators
import com.mongodb.client.model.Updates
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.thechance.service_restaurant.data.Constants.CATEGORY_COLLECTION
import org.thechance.service_restaurant.data.Constants.CUISINE_COLLECTION
import org.thechance.service_restaurant.data.Constants.RESTAURANT_COLLECTION
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.*
import org.thechance.service_restaurant.data.collection.mapper.toCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.data.utils.getNonEmptyFieldsMap
import org.thechance.service_restaurant.data.utils.isSuccessfullyUpdated
import org.thechance.service_restaurant.data.utils.paginate
import org.thechance.service_restaurant.data.utils.toObjectIds
import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.RestaurantGateway

@Single
class RestaurantGatewayImp(private val container: DataBaseContainer) : RestaurantGateway {

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

    override suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category> {
        return container.restaurantCollection.aggregate<CategoryRestaurant>(
            match(RestaurantCollection::id eq ObjectId(restaurantId)),
            lookup(
                from = CATEGORY_COLLECTION,
                localField = RestaurantCollection::categoryIds.name,
                foreignField = "_id",
                newAs = CategoryRestaurant::categories.name
            )
        ).toList().first().categories.filterNot { it.isDeleted }.toEntity()
    }

    override suspend fun getCuisineInRestaurant(restaurantId: String): List<Cuisine> {
        return container.restaurantCollection.aggregate<RestaurantCuisine>(
            match(RestaurantCollection::id eq ObjectId(restaurantId)),
            lookup(
                from = CUISINE_COLLECTION,
                localField = RestaurantCollection::cuisineIds.name,
                foreignField = "_id",
                newAs = RestaurantCuisine::cuisines.name
            )
        ).toList().first().cuisines.filterNot { it.isDeleted }.toEntity()
    }

    override suspend fun addRestaurant(restaurant: Restaurant): Boolean {
        return container.restaurantCollection.insertOne(restaurant.toCollection()).wasAcknowledged()
    }

    override suspend fun addCategoriesToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        val resultAddToCategory = container.categoryCollection.updateMany(
            CategoryCollection::id `in` categoryIds.toObjectIds(),
            addToSet(CategoryCollection::restaurantIds, ObjectId(restaurantId))
        ).isSuccessfullyUpdated()

        val resultAddToRestaurant = container.restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            update = Updates.addEachToSet(RestaurantCollection::categoryIds.name, categoryIds.toObjectIds())
        ).isSuccessfullyUpdated()

        return resultAddToCategory and resultAddToRestaurant
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
        val updateFields = getNonEmptyFieldsMap(restaurant.copy(id = "", ownerId = ""))
        return container.restaurantCollection.updateOneById(
            id = ObjectId(restaurant.id),
            update = updateFields,
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

    override suspend fun deleteCuisinesFromRestaurant(restaurantId: String, cuisineIds: List<String>): Boolean {
        val deletedCuisineIds = container.mealCollection.aggregate<MealCollection>(
            match(
                and(
                    MealCollection::restaurantId eq ObjectId(restaurantId),
                    MealCollection::isDeleted eq false
                )
            ),
            lookup(
                from = RESTAURANT_COLLECTION,
                localField = MealCollection::restaurantId.name,
                foreignField = "_id",
                newAs = MealCollection::cuisines.name
            ),
            unwind(MealCollection::cuisines.name),
            group(MealCollection::cuisines, Accumulators.addToSet("_id", "\$_id")),
        ).toList().flatMap { it.cuisines }.filter { it.toString() in cuisineIds }
        return deleteCuisinesInRestaurant(restaurantId, deletedCuisineIds.map { it.toString() })
    }
    //endregion
}