package org.thechance.service_restaurant.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.CuisineGateway

@Single
class AddMealsToCuisineUseCaseImp(private val cuisineGateway: CuisineGateway) : AddMealsToCuisineUseCase {
    override suspend fun invoke(cuisineId: String, mealIds: List<String>): Boolean {
        return cuisineGateway.addMealsToCuisine(cuisineId, mealIds)
    }
}