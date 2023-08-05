package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Restaurant

interface AdministratorUseCase {
    suspend fun updateCuisine(cuisine: Cuisine): Boolean
    suspend fun deleteCuisine(id: String): Boolean
    suspend fun addCuisine(cuisine: Cuisine): Boolean
    suspend fun getCuisinesWithMeals(): List<Cuisine>
    suspend fun updateRestaurant(restaurant: Restaurant): Boolean
    suspend fun createRestaurant(restaurant: Restaurant): Boolean
    suspend fun deleteRestaurant(restaurantId: String): Boolean
    suspend fun deleteRestaurantsInCategory(categoryId: String, restaurantIds: List<String>): Boolean

    suspend fun addRestaurantsToCategory(categoryId: String, restaurantIds: List<String>): Boolean
    suspend fun createCategory(category: Category): Boolean
    suspend fun updateCategory(category: Category): Boolean

    suspend fun deleteCategory(categoryId: String): Boolean

}