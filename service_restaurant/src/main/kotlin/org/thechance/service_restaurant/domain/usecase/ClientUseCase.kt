package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.Restaurant

interface ClientUseCase {
    suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant>
    suspend fun getCategories(page: Int, limit: Int): List<Category>
    suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category>
    suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant>
    suspend fun getRestaurantDetails(restaurantId: String): Restaurant
    suspend fun getCuisinesWithMeals(page: Int, limit: Int): List<Cuisine>
    suspend fun getMealsInCuisines(cuisineId: String): List<Cuisine>
    suspend fun getMealDetails(mealId: String): Meal
}