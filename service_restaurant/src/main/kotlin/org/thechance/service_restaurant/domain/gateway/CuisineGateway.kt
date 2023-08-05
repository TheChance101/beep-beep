package org.thechance.service_restaurant.domain.gateway

import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Meal

interface CuisineGateway {

    suspend fun getCuisines(page: Int, limit: Int): List<Cuisine>
    suspend fun getCuisineById(id: String): Cuisine?
    suspend fun getMealsInCuisine(cuisineId: String): List<Meal>
    suspend fun addCuisine(cuisine: Cuisine): Boolean
    suspend fun updateCuisine(cuisine: Cuisine): Boolean
    suspend fun deleteCuisine(id: String): Boolean

}