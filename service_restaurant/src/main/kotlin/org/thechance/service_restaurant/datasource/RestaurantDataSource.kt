package org.thechance.service_restaurant.datasource

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.entity.Restaurant

@Single
class RestaurantDataSource(private val dataBase: DataBase) {

    private val restaurantCollection by lazy {
        dataBase.database.getCollection<Restaurant>()
    }

    suspend fun getRestaurant(): List<Restaurant> {
        return restaurantCollection.find().toList()
    }

    suspend fun addRestaurant(name: String): Boolean {
        return restaurantCollection.insertOne(Restaurant(name = name)).wasAcknowledged()
    }

}