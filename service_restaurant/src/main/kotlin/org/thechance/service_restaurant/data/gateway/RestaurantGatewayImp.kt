package org.thechance.service_restaurant.data.gateway

import com.mongodb.client.model.Updates
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.eq
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.RestaurantCollection
import org.thechance.service_restaurant.data.collection.toEntity
import org.thechance.service_restaurant.entity.Restaurant
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway
import org.thechance.service_restaurant.utils.isSuccessfullyUpdated

@Single
class RestaurantGatewayImp(private val container: DataBaseContainer) : RestaurantGateway {

    private val restaurantCollection by lazy { container.database.getCollection<RestaurantCollection>() }

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