package org.thechance.service_restaurant.data.gateway

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.DataBase
import org.thechance.service_restaurant.data.collection.RestaurantCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway
import org.thechance.service_restaurant.entity.Restaurant

@Single
class RestaurantGatewayImp(private val dataBase: DataBase) : RestaurantGateway {

    private val restaurantCollection by lazy {
        dataBase.database.getCollection<RestaurantCollection>()
    }

    override suspend fun getRestaurant(): List<Restaurant> {
        return restaurantCollection.find().toList().toEntity()
    }

    override suspend fun addRestaurant(name: String): Boolean {
        return restaurantCollection.insertOne(RestaurantCollection(name = name)).wasAcknowledged()
    }



}