package org.thechance.service_restaurant.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.CuisineGateway
import org.thechance.service_restaurant.entity.Meal

@Single
class GetMealsInCuisineUseCaseImp(private val cuisineGateway: CuisineGateway) : GetMealsInCuisineUseCase {
    override suspend fun invoke(cuisineId: String): List<Meal> {
        return cuisineGateway.getMealsInCuisine(cuisineId)
    }
}