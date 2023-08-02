package org.thechance.service_restaurant.usecase.gateway

import org.thechance.service_restaurant.entity.Cuisine
import org.thechance.service_restaurant.entity.Meal

interface MealGateway {

    suspend fun addMeal(meal: Meal): Boolean

    suspend fun getMeals(page: Int, limit: Int): List<Meal>

    suspend fun getMealById(id: String): Meal?

    suspend fun deleteMealById(id: String): Boolean

    suspend fun updateMeal(meal: Meal): Boolean

    suspend fun addCuisineToMeal(mealId: String, cuisineId: String): Boolean

    suspend fun getMealCuisines(mealId: String): List<Cuisine>

    suspend fun deleteCuisineFromMeal(mealId: String, cuisineId: String): Boolean

}