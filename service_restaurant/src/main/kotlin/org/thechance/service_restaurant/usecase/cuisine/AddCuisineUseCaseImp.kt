package org.thechance.service_restaurant.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.entity.Cuisine
import org.thechance.service_restaurant.data.gateway.CuisineGateway

@Single
class AddCuisineUseCaseImp(private val gateway: CuisineGateway) : AddCuisineUseCase {
    override suspend fun invoke(cuisine: Cuisine) = gateway.addCuisine(cuisine)

}