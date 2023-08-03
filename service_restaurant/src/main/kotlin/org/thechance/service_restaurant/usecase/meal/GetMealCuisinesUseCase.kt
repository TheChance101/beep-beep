package org.thechance.service_restaurant.usecase.meal

import org.thechance.service_restaurant.entity.Cuisine

interface GetMealCuisinesUseCase {

    suspend operator fun invoke(mealId: String): List<Cuisine>

}