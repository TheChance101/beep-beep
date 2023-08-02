package org.thechance.service_restaurant.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.entity.Cuisine
import org.thechance.service_restaurant.usecase.gateway.CuisineGateway

@Single
class UpdateCuisineUseCaseImp(private val gateway: CuisineGateway) : UpdateCuisineUseCase {
    
    override suspend fun invoke(cuisine: Cuisine) = gateway.updateCuisine(cuisine)

}