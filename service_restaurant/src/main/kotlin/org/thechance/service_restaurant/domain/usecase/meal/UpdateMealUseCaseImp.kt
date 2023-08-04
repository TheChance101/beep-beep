package org.thechance.service_restaurant.domain.usecase.meal

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.gateway.MealGateway

@Single
class UpdateMealUseCaseImp(private val mealGateway: MealGateway) : UpdateMealUseCase {
    override suspend fun invoke(meal: Meal): Boolean = mealGateway.updateMeal(meal)

}