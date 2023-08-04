package org.thechance.service_restaurant.domain.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.gateway.MealGateway
import org.thechance.service_restaurant.domain.entity.Cuisine

@Single
class GetMealCuisinesUseCaseImp(private val mealGateway: MealGateway) : GetMealCuisinesUseCase {

    override suspend fun invoke(mealId: String): List<Cuisine> = mealGateway.getMealCuisines(mealId)

}