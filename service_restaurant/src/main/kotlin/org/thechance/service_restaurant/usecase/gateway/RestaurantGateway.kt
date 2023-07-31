package org.thechance.service_restaurant.usecase.gateway

import org.thechance.service_restaurant.entity.Restaurant

interface RestaurantGateway {
    suspend fun getRestaurants(): List<Restaurant>
    suspend fun getRestaurant(id: String): Restaurant?
    suspend fun addRestaurant(restaurant: Restaurant): Boolean
    suspend fun updateRestaurant(restaurant: Restaurant): Boolean
    suspend fun deleteRestaurant(restaurantId: String): Boolean


}