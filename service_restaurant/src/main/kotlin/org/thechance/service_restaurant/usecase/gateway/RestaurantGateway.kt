package org.thechance.service_restaurant.usecase.gateway

import org.thechance.service_restaurant.entity.Restaurant

interface RestaurantGateway {

    suspend fun getRestaurant(): List<Restaurant>

    suspend fun addRestaurant(name: String): Boolean

}