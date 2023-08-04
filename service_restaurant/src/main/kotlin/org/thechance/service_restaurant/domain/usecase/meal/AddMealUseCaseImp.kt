package org.thechance.service_restaurant.domain.usecase.meal

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.gateway.MealGateway

@Single
class AddMealUseCaseImp(private val mealGateway: MealGateway) : AddMealUseCase {
    override suspend fun invoke(meal: Meal) = mealGateway.addMeal(meal)

}