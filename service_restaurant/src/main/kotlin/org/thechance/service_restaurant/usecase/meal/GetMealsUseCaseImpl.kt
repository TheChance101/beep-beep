package org.thechance.service_restaurant.usecase.meal

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.entity.Meal
import org.thechance.service_restaurant.usecase.gateway.MealGateway

@Single
class GetMealsUseCaseImpl(private val mealGateway: MealGateway) : GetMealsUseCase {
    override suspend fun invoke(page: Int , limit : Int): List<Meal> = mealGateway.getMeals(page , limit)

}