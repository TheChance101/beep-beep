package org.thechance.service_restaurant.domain.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.gateway.MealGateway

@Single
class AddCuisineToMealUseCaseImp(private val mealGateway: MealGateway) : AddCuisinesToMealUseCase {

    override suspend fun invoke(mealId: String, cuisineIds: List<String>): Boolean =
        mealGateway.addCuisinesToMeal(mealId, cuisineIds)

}