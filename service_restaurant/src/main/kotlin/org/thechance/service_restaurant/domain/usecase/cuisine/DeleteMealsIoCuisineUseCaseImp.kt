package org.thechance.service_restaurant.domain.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.gateway.CuisineGateway

@Single
class DeleteMealsIoCuisineUseCaseImp(private val cuisineGateway: CuisineGateway) : DeleteMealsIoCuisineUseCase {
    override suspend fun invoke(cuisineId: String, mealIds: List<String>): Boolean {
        return cuisineGateway.deleteMealsInCuisine(cuisineId, mealIds)
    }
}