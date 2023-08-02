package org.thechance.service_restaurant.usecase.meal

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.entity.Meal
import org.thechance.service_restaurant.data.gateway.MealGateway

@Single
class AddMealUseCaseImp(private val mealGateway: MealGateway) : AddMealUseCase {
    override suspend fun invoke(meal: Meal) = mealGateway.addMeal(meal)

}