package org.thechance.service_restaurant.data.gateway

import org.thechance.service_restaurant.entity.Address
import org.thechance.service_restaurant.entity.Category
import org.thechance.service_restaurant.entity.Restaurant

interface RestaurantGateway {

    //region restaurant
    suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant>
    suspend fun getRestaurant(id: String): Restaurant?
    suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category>
    suspend fun addRestaurant(restaurant: Restaurant): Boolean
    suspend fun addCategoriesToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean
    suspend fun updateRestaurant(restaurant: Restaurant): Boolean
    suspend fun deleteRestaurant(restaurantId: String): Boolean
    suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean
    //endregion

    //region addresses
    suspend fun addAddressesToRestaurant(restaurantId: String, addressesIds: List<String>): Boolean
    suspend fun getAddressesInRestaurant(restaurantId: String): List<Address>
    suspend fun deleteAddressesInRestaurant(restaurantId: String, addressesIds: List<String>): Boolean
    //endregion
}