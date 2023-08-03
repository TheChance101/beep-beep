package org.thechance.service_restaurant.data.gateway

import com.mongodb.client.model.Updates
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.CategoryCollection
import org.thechance.service_restaurant.data.collection.CategoryRestaurantCollection
import org.thechance.service_restaurant.data.collection.RestaurantCollection
import org.thechance.service_restaurant.data.collection.toEntity
import org.thechance.service_restaurant.data.utils.isSuccessfullyUpdated
import org.thechance.service_restaurant.data.utils.paginate
import org.thechance.service_restaurant.data.utils.toObjectIds
import org.thechance.service_restaurant.entity.Category
import org.thechance.service_restaurant.entity.Restaurant

@Single
class RestaurantGatewayImp(private val container: DataBaseContainer) : RestaurantGateway {

    private val restaurantCollection by lazy { container.database.getCollection<RestaurantCollection>() }

    private val categoryCollection by lazy { container.database.getCollection<CategoryCollection>() }

    //region Category
    override suspend fun getCategories(page: Int, limit: Int): List<Category> {
        return categoryCollection.find(CategoryCollection::isDeleted eq false)
            .paginate(page, limit).toList().toEntity()
    }

    override suspend fun getCategory(categoryId: String): Category? {
        return categoryCollection.aggregate<CategoryCollection>(
            match(and(CategoryCollection::id eq ObjectId(categoryId), CategoryCollection::isDeleted eq false)),
            project(CategoryCollection::name, CategoryCollection::id)
        ).toList().firstOrNull()?.toEntity()
    }

    override suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant> {
        return categoryCollection.aggregate<CategoryRestaurantCollection>(
            match(CategoryCollection::id eq ObjectId(categoryId)),
            lookup(
                from = "restaurantCollection",
                resultProperty = CategoryRestaurantCollection::restaurants,
                pipeline = listOf(match(RestaurantCollection::isDeleted eq false)).toTypedArray()
            ),
        ).toList().first().restaurants.toEntity()
    }

    override suspend fun addCategory(category: Category): Boolean {
        return categoryCollection.insertOne(category.toCollection()).wasAcknowledged()
    }

    override suspend fun addRestaurantsToCategory(categoryId: String, restaurantIds: List<String>): Boolean {
        val resultAddToRestaurant = restaurantCollection.updateMany(
            RestaurantCollection::id `in` restaurantIds.toObjectIds(),
            addToSet(RestaurantCollection::categoryIds, ObjectId(categoryId))
        ).isSuccessfullyUpdated()

        val resultAddToCategory = categoryCollection.updateOneById(
            ObjectId(categoryId),
            update = Updates.addEachToSet(CategoryCollection::restaurantIds.name, restaurantIds.toObjectIds())
        ).isSuccessfullyUpdated()
        return resultAddToCategory and resultAddToRestaurant
    }

    override suspend fun updateCategory(category: Category): Boolean {
        return categoryCollection.updateOneById(
            id = ObjectId(category.id),
            update = category.toCollection(),
            updateOnlyNotNullProperties = true
        ).isSuccessfullyUpdated()
    }

    override suspend fun deleteCategory(categoryId: String): Boolean {
        return categoryCollection.updateOneById(
            id = ObjectId(categoryId),
            update = Updates.set(CategoryCollection::isDeleted.name, true),
        ).isSuccessfullyUpdated()
    }

    override suspend fun deleteRestaurantsInCategory(categoryId: String, restaurantIds: List<String>): Boolean {
        val resultDeleteFromRestaurant = restaurantCollection.updateMany(
            RestaurantCollection::id `in` restaurantIds.toObjectIds(),
            pull(RestaurantCollection::categoryIds, ObjectId(categoryId))
        ).isSuccessfullyUpdated()

        val resultDeleteFromCategory = categoryCollection.updateOneById(
            ObjectId(categoryId),
            pullAll(CategoryCollection::restaurantIds, restaurantIds.toObjectIds())
        ).isSuccessfullyUpdated()
        return resultDeleteFromRestaurant and resultDeleteFromCategory
    }
    //endregion

    //region Restaurant
    override suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant> {
        return restaurantCollection.find(RestaurantCollection::isDeleted eq false)
            .paginate(page, limit).toList().toEntity()
    }

    override suspend fun getRestaurant(id: String): Restaurant? {
        return restaurantCollection.aggregate<RestaurantCollection>(
            match(and(RestaurantCollection::id eq ObjectId(id), RestaurantCollection::isDeleted eq false)),
        ).toList().firstOrNull()?.toEntity()
    }

    override suspend fun addRestaurant(restaurant: Restaurant): Boolean {
        return restaurantCollection.insertOne(restaurant.toCollection()).wasAcknowledged()
    }

    override suspend fun addCategoriesToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        val resultAddToCategory = categoryCollection.updateMany(
            CategoryCollection::id `in` categoryIds.toObjectIds(),
            addToSet(CategoryCollection::restaurantIds, ObjectId(restaurantId))
        ).isSuccessfullyUpdated()

        val resultAddToRestaurant = restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            update = Updates.addEachToSet(RestaurantCollection::categoryIds.name, categoryIds.toObjectIds())
        ).isSuccessfullyUpdated()

        return resultAddToCategory and resultAddToRestaurant
    }

    override suspend fun updateRestaurant(restaurant: Restaurant): Boolean {
        return restaurantCollection.updateOneById(
            id = ObjectId(restaurant.id),
            update = restaurant.toCollection(),
            updateOnlyNotNullProperties = true
        ).isSuccessfullyUpdated()
    }

    override suspend fun deleteRestaurant(restaurantId: String): Boolean {
        return restaurantCollection.updateOneById(
            id = ObjectId(restaurantId),
            update = Updates.set(RestaurantCollection::isDeleted.name, true),
        ).isSuccessfullyUpdated()
    }

    override suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        val resultDeleteFromCategory = categoryCollection.updateMany(
            CategoryCollection::id `in` categoryIds.toObjectIds(),
            pull(CategoryCollection::restaurantIds, ObjectId(restaurantId))
        ).isSuccessfullyUpdated()

        val resultDeleteFromRestaurant = restaurantCollection.updateOneById(
            ObjectId(restaurantId),
            pullAll(RestaurantCollection::categoryIds, categoryIds.toObjectIds())
        ).isSuccessfullyUpdated()
        return resultDeleteFromRestaurant and resultDeleteFromCategory
    }
    //endregion
}