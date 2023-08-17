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

    suspend fun areCategoriesExisting(categoryIds: List<String>): Boolean
    suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category>
    suspend fun addCategoriesToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean
    suspend fun addCategory(category: Category): Category
    suspend fun updateCategory(category: Category): Category
    suspend fun deleteCategory(categoryId: String): Boolean
    suspend fun deleteRestaurantsInCategory(categoryId: String, restaurantIds: List<String>): Boolean

    //endregion

    //region Cuisines
    suspend fun getCuisines(): List<Cuisine>
    suspend fun getCuisineById(id: String): Cuisine?
    suspend fun getMealsInCuisine(cuisineId: String): List<Meal>
    suspend fun addCuisine(cuisine: Cuisine): Cuisine
    suspend fun areCuisinesExist(cuisineIds: List<String>): Boolean
    suspend fun updateCuisine(cuisine: Cuisine): Cuisine
    suspend fun deleteCuisine(id: String): Boolean
    //endregion
}