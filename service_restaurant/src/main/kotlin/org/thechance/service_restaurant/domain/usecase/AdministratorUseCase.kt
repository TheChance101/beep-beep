package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.*

interface AdministratorUseCase {
    suspend fun getAllMeals(page: Int, limit: Int): List<Meal>
    suspend fun updateCuisine(cuisine: Cuisine): Boolean
    suspend fun deleteCuisine(id: String): Boolean
    suspend fun addCuisine(cuisine: Cuisine): Boolean
    suspend fun createRestaurant(restaurant: Restaurant): Boolean
    suspend fun deleteRestaurant(restaurantId: String): Boolean
    suspend fun deleteRestaurantsInCategory(categoryId: String, restaurantIds: List<String>): Boolean

    suspend fun addRestaurantsToCategory(categoryId: String, restaurantIds: List<String>): Boolean
    suspend fun createCategory(category: Category): Boolean
    suspend fun updateCategory(category: Category): Boolean

    suspend fun deleteCategory(categoryId: String?): Boolean

}