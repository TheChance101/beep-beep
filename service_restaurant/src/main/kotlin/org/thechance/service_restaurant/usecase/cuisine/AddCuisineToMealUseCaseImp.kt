package org.thechance.service_restaurant.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.usecase.gateway.MealGateway
import org.thechance.service_restaurant.usecase.meal.AddCuisineToMealUseCase

@Single
class AddCuisineToMealUseCaseImp(private val mealGateway: MealGateway) : AddCuisineToMealUseCase {
    
    override suspend fun invoke(mealId: String, cuisineId: String): Boolean =
        mealGateway.addCuisineToMeal(mealId, cuisineId)

}