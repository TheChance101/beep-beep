package org.thechance.service_restaurant.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.MealGateway
import org.thechance.service_restaurant.usecase.meal.DeleteCuisineFromMealUseCase

@Single
class DeleteCuisineFromMealUseCaseImp(private val gateway: MealGateway) : DeleteCuisineFromMealUseCase {
    override suspend fun invoke(mealId: String, cuisineId: String): Boolean {
        return gateway.deleteCuisineFromMeal(mealId, cuisineId)
    }
}