package org.thechance.service_restaurant.domain.usecase.cuisine

import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Meal

interface UserGetCuisineUseCase {
    suspend fun getMealsInCuisine(cuisineId:String): List<Meal>

    suspend fun getCuisines(): List<Cuisine>
}