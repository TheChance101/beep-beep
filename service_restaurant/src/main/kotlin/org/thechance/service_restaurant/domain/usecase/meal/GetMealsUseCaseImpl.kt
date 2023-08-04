package org.thechance.service_restaurant.domain.usecase.meal

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.gateway.MealGateway

@Single
class GetMealsUseCaseImpl(private val mealGateway: MealGateway) : GetMealsUseCase {
    override suspend fun invoke(page: Int , limit : Int): List<Meal> = mealGateway.getMeals(page , limit)

}