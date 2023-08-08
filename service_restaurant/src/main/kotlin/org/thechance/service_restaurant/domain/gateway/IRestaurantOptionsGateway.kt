package org.thechance.service_restaurant.domain.gateway

import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.Restaurant

interface IRestaurantOptionsGateway {
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

    //region Cuisines
    suspend fun getCuisines(page: Int, limit: Int): List<Cuisine>
    suspend fun getCuisineById(id: String): Cuisine?
    suspend fun getMealsInCuisine(cuisineId: String): List<Meal>
    suspend fun addCuisine(cuisine: Cuisine): Boolean
    suspend fun updateCuisine(cuisine: Cuisine): Boolean
    suspend fun deleteCuisine(id: String): Boolean
    //endregion
}