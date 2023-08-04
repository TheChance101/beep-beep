package org.thechance.service_restaurant.domain.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.gateway.CuisineGateway

@Single
class UpdateCuisineUseCaseImp(private val gateway: CuisineGateway) : UpdateCuisineUseCase {
    
    override suspend fun invoke(cuisine: Cuisine) = gateway.updateCuisine(cuisine)

}