package org.thechance.service_restaurant.domain.usecase.meal

import org.thechance.service_restaurant.domain.entity.Cuisine

interface ManageCuisinesInMealUseCase {
    suspend fun addCuisinesToMeal(mealId: String, cuisineIds: List<String>): Boolean
    suspend fun deleteCuisineFromMeal(mealId: String, cuisineId: String): Boolean
    suspend fun getMealCuisines(mealId: String): List<Cuisine>
}