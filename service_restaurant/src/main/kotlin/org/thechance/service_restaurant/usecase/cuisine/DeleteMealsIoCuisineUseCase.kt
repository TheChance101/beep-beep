package org.thechance.service_restaurant.usecase.cuisine

import org.thechance.service_restaurant.entity.Cuisine

interface DeleteMealsIoCuisineUseCase {
    suspend operator fun invoke(cuisineId: String, mealIds: List<String>): Boolean
}