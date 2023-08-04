package org.thechance.service_restaurant.domain.usecase.restaurant

import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Restaurant

interface ManageRestaurantUseCase {
    suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant>
    suspend fun getRestaurantDetails(restaurantId: String): Restaurant
    suspend fun createRestaurant(restaurant: Restaurant): Boolean
    suspend fun updateRestaurant(restaurant: Restaurant): Boolean
    suspend fun deleteRestaurant(restaurantId: String): Boolean
    suspend fun addCategoryToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean
    suspend fun addCuisinesToRestaurant(restaurantId: String, cuisineIds: List<String>): Boolean
    suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean
    suspend fun deleteCuisinesInRestaurant(restaurantId: String, cuisineIds: List<String>): Boolean
    suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category>
    suspend fun getCuisinesInRestaurant(restaurantId: String): List<Cuisine>

}