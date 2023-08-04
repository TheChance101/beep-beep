package org.thechance.service_restaurant.domain.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.gateway.CuisineGateway

@Single
class ManageCuisineUseCaseImp(private val cuisineGateway: CuisineGateway):ManageCuisineUseCase {
    override suspend fun getCuisines(page: Int, limit: Int): List<Cuisine> {
        return cuisineGateway.getCuisines(page, limit)
    }

}