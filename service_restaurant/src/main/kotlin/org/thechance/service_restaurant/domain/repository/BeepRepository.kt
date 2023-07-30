package org.thechance.service_restaurant.domain.repository

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.datasource.RestaurantDataSource
import org.thechance.service_restaurant.entity.Restaurant

@Single
class BeepRepository(private val restaurant: RestaurantDataSource) {

    suspend fun getRestaurants(): List<Restaurant> {
        return restaurant.getRestaurant()
    }

    suspend fun addRestaurants(name: String): Boolean {
        return restaurant.addRestaurant(name = name)
    }

}