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
import org.thechance.service_restaurant.data.utils.paginate
import org.thechance.service_restaurant.entity.Category
import org.thechance.service_restaurant.entity.Restaurant
import org.thechance.service_restaurant.utils.isSuccessfullyUpdated

@Single
class RestaurantGatewayImp(private val container: DataBaseContainer) : RestaurantGateway {

    private val restaurantCollection by lazy { container.database.getCollection<RestaurantCollection>() }

    private val categoryCollection by lazy {
        container.database.getCollection<CategoryCollection>()
    }

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
        return categoryCollection.updateOneById(
            ObjectId(categoryId),
            update = Updates.addEachToSet(CategoryCollection::restaurantIds.name, restaurantIds.map { ObjectId(it) })
        ).isSuccessfullyUpdated()
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
        return categoryCollection.updateOneById(
            ObjectId(categoryId),
            pullAll(CategoryCollection::restaurantIds, restaurantIds.map { ObjectId(it) })
        ).isSuccessfullyUpdated()
    }


    //endregion

    //region Restaurant
    override suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant> {
        return restaurantCollection.find(RestaurantCollection::isDeleted eq false)
            .paginate(page, limit).toList().toEntity()
    }

    override suspend fun getRestaurant(id: String): Restaurant? {
        return restaurantCollection.findOneById(ObjectId(id))?.takeIf { !it.isDeleted }?.toEntity()
    }

    override suspend fun addRestaurant(restaurant: Restaurant): Boolean {
        return restaurantCollection.insertOne(restaurant.toCollection()).wasAcknowledged()
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
    //endregion
}