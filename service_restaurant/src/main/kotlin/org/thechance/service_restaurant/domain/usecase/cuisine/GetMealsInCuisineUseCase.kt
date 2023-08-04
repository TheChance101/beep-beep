package org.thechance.service_restaurant.domain.usecase.cuisine

import org.thechance.service_restaurant.domain.entity.Meal

interface GetMealsInCuisineUseCase {
    suspend operator fun invoke(cuisineId: String): List<Meal>
}