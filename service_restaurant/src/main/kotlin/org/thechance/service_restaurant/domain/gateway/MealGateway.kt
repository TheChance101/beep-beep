package org.thechance.service_restaurant.domain.gateway

import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.MealDetails

interface MealGateway {

    suspend fun addMeal(meal: MealDetails): Boolean

    suspend fun getMeals(page: Int, limit: Int): List<Meal>

    suspend fun getMealById(id: String): MealDetails?

    suspend fun deleteMealById(id: String): Boolean

    suspend fun updateMeal(meal: MealDetails): Boolean

    suspend fun addCuisinesToMeal(mealId: String, cuisineIds: List<String>): Boolean

    suspend fun getMealCuisines(mealId: String): List<Cuisine>

    suspend fun deleteCuisineFromMeal(mealId: String, cuisineId: String): Boolean

}