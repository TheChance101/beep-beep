package org.thechance.service_restaurant.domain.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.gateway.MealGateway
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.usecase.meal.GetMealCuisinesUseCase

@Single
class GetMealCuisinesUseCaseImp(private val mealGateway: MealGateway) : GetMealCuisinesUseCase {

    override suspend fun invoke(mealId: String): List<Cuisine> = mealGateway.getMealCuisines(mealId)

}