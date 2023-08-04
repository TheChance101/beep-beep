package org.thechance.service_restaurant.domain.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.gateway.MealGateway

@Single
class DeleteCuisineFromMealUseCaseImp(private val gateway: MealGateway) : DeleteCuisineFromMealUseCase {
    override suspend fun invoke(mealId: String, cuisineId: String): Boolean {
        return gateway.deleteCuisineFromMeal(mealId, cuisineId)
    }
}