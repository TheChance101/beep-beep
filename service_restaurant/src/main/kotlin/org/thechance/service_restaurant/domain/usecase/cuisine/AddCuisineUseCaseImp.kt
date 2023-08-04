package org.thechance.service_restaurant.domain.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.gateway.CuisineGateway

@Single
class AddCuisineUseCaseImp(private val gateway: CuisineGateway) : AddCuisineUseCase {
    override suspend fun invoke(cuisine: Cuisine) = gateway.addCuisine(cuisine)

}