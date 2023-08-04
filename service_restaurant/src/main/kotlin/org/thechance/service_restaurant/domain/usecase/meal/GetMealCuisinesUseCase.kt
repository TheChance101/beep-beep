package org.thechance.service_restaurant.domain.usecase.meal

import org.thechance.service_restaurant.domain.entity.Cuisine

interface GetMealCuisinesUseCase {

    suspend operator fun invoke(mealId: String): List<Cuisine>

}