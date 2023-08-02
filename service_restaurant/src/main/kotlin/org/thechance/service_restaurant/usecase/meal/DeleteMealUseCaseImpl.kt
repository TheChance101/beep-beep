package org.thechance.service_restaurant.usecase.meal

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.DeleteMealUseCase
import org.thechance.service_restaurant.usecase.gateway.MealGateway

@Single
class DeleteMealUseCaseImpl(private val mealGateway: MealGateway) : DeleteMealUseCase {
    override suspend fun invoke(id: String) : Boolean  = mealGateway.deleteMealById(id)


}