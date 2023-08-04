package org.thechance.service_restaurant.data.gateway

import org.thechance.service_restaurant.entity.Cuisine
import org.thechance.service_restaurant.entity.Meal

interface CuisineGateway {

    suspend fun getCuisines(page: Int, limit: Int): List<Cuisine>
    suspend fun getCuisineById(id: String): Cuisine?
    suspend fun getMealsInCuisine(cuisineId: String): List<Meal>

    suspend fun addCuisine(cuisine: Cuisine): Boolean
    suspend fun addMealsToCuisine(cuisineId: String, mealIds: List<String>): Boolean
    suspend fun updateCuisine(cuisine: Cuisine): Boolean

    suspend fun deleteCuisine(id: String): Boolean
    suspend fun deleteMealsInCuisine(cuisineId: String, mealIds: List<String>): Boolean


}