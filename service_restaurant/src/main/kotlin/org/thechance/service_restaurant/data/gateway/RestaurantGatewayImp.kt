package org.thechance.service_restaurant.data.gateway

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.RestaurantCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway
import org.thechance.service_restaurant.entity.Restaurant

@Single
class RestaurantGatewayImp(private val container: DataBaseContainer) : RestaurantGateway {

    private val restaurantCollection by lazy {
        container.database.getCollection<RestaurantCollection>()
    }

    override suspend fun getRestaurants(): List<Restaurant> {
        return restaurantCollection.find().toList().toEntity()
    }

    override suspend fun addRestaurant(name: String): Boolean {
        return restaurantCollection.insertOne(RestaurantCollection(name = name)).wasAcknowledged()
    }


}