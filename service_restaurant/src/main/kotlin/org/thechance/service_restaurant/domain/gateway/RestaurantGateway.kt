package org.thechance.service_restaurant.domain.gateway

import org.thechance.service_restaurant.domain.entity.*

interface RestaurantGateway {

    //region Get
    suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant>
    suspend fun getRestaurant(id: String): Restaurant?
    suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category>
    suspend fun getCuisineInRestaurant(restaurantId: String): List<Cuisine>

    //endregion

    //region Add
    suspend fun addRestaurant(restaurant: Restaurant): Boolean
    suspend fun addCategoriesToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean
    suspend fun addCuisineToRestaurant(restaurantId: String, cuisineIds: List<String>): Boolean
    suspend fun addMealToRestaurant(restaurantId: String, mealId: String): Boolean
    //endregion


    suspend fun updateRestaurant(restaurant: Restaurant): Boolean

    //region delete
    suspend fun deleteRestaurant(restaurantId: String): Boolean
    suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean
    suspend fun deleteCuisinesInRestaurant(restaurantId: String, cuisineIds: List<String>): Boolean
    suspend fun deleteMealInRestaurant(restaurantId: String, mealId: String): Boolean
    suspend fun deleteCuisinesFromRestaurant(restaurantId: String, cuisineIds: List<String>): Boolean
    //endregion

    //region addresses
    suspend fun addAddressesToRestaurant(restaurantId: String, addressesIds: List<String>): Boolean
    suspend fun getAddressesInRestaurant(restaurantId: String): List<Address>
    suspend fun deleteAddressesInRestaurant(restaurantId: String, addressesIds: List<String>): Boolean
    //endregion
}