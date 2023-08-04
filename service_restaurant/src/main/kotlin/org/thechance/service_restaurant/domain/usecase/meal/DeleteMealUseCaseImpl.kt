package org.thechance.service_restaurant.domain.usecase.meal

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.gateway.MealGateway

@Single
class DeleteMealUseCaseImpl(private val mealGateway: MealGateway) : DeleteMealUseCase {
    override suspend fun invoke(id: String) : Boolean  = mealGateway.deleteMealById(id)


}