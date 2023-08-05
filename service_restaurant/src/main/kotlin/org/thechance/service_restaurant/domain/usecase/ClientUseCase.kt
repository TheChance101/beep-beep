package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.*

interface ClientUseCase {
    suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant>
    suspend fun getCategories(page: Int, limit: Int): List<Category>
    suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category>
    suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant>
    suspend fun getRestaurantDetails(restaurantId: String): Restaurant
    suspend fun getMealsInCuisines(cuisineId: String): List<Meal>
    suspend fun getMealDetails(mealId: String): MealDetails
}