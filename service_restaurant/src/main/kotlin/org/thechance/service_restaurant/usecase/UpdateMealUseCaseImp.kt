package org.thechance.service_restaurant.usecase

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.UpdateMealUseCase
import org.thechance.service_restaurant.entity.Meal
import org.thechance.service_restaurant.usecase.gateway.MealGateway

@Single
class UpdateMealUseCaseImp(private val mealGateway: MealGateway) : UpdateMealUseCase {
    override suspend fun invoke(meal: Meal): Boolean = mealGateway.updateMeal(meal)

}