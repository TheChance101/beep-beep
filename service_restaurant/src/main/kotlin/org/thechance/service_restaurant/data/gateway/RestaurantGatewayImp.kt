package org.thechance.service_restaurant.data.gateway

import com.mongodb.client.model.Updates
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.eq
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.CategoryCollection
import org.thechance.service_restaurant.data.collection.RestaurantCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.data.collection.toEntity
import org.thechance.service_restaurant.entity.Category
import org.thechance.service_restaurant.entity.Restaurant
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway
import org.thechance.service_restaurant.utils.isSuccessfullyUpdated

@Single
class RestaurantGatewayImp(private val container: DataBaseContainer) : RestaurantGateway {

    private val restaurantCollection by lazy { container.database.getCollection<RestaurantCollection>() }

    private val categoryCollection by lazy {
        container.database.getCollection<CategoryCollection>()
    }

    //region Category
    override suspend fun getCategories(): List<Category> {
        return categoryCollection.find(CategoryCollection::isDeleted eq false).toList().toEntity()
    }

    override suspend fun getCategory(categoryId: String): Category? {
        return categoryCollection.findOneById(ObjectId(categoryId))?.takeIf { !it.isDeleted }?.toEntity()
    }

    override suspend fun addCategory(category: Category): Boolean {
        return categoryCollection.insertOne(category.toCollection()).wasAcknowledged()
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
    //endregion

    override suspend fun getRestaurants(): List<Restaurant> {
        return restaurantCollection.find(RestaurantCollection::isDeleted eq false).toList().toEntity()
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

}