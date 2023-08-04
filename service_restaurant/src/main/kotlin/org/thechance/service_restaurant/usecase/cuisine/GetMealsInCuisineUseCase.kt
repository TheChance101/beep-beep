package org.thechance.service_restaurant.usecase.cuisine

import org.thechance.service_restaurant.entity.Meal

interface GetMealsInCuisineUseCase {
    suspend operator fun invoke(cuisineId: String): List<Meal>
}