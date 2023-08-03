package org.thechance.service_restaurant.data.gateway

import org.thechance.service_restaurant.entity.Category
import org.thechance.service_restaurant.entity.Restaurant

interface RestaurantGateway {

    //region Category
    suspend fun getCategories(page: Int, limit: Int): List<Category>
    suspend fun getCategory(categoryId: String): Category?
    suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant>
    suspend fun addRestaurantsToCategory(categoryId: String, restaurantIds: List<String>): Boolean
    suspend fun addCategory(category: Category): Boolean
    suspend fun updateCategory(category: Category): Boolean
    suspend fun deleteCategory(categoryId: String): Boolean
    suspend fun deleteRestaurantsInCategory(categoryId: String, restaurantIds: List<String>): Boolean

    //endregion

    //region restaurant
    suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant>
    suspend fun getRestaurant(id: String): Restaurant?
    suspend fun addRestaurant(restaurant: Restaurant): Boolean
    suspend fun addCategoriesToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean
    suspend fun updateRestaurant(restaurant: Restaurant): Boolean
    suspend fun deleteRestaurant(restaurantId: String): Boolean
    suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean
    //endregion

}