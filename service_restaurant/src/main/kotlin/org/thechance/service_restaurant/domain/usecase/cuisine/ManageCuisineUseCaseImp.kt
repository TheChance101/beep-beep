package org.thechance.service_restaurant.domain.usecase.cuisine

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.gateway.CuisineGateway

@Single
class ManageCuisineUseCaseImp(private val cuisineGateway: CuisineGateway):ManageCuisineUseCase {
    override suspend fun updateCuisine(cuisine: Cuisine): Boolean {
        return cuisineGateway.updateCuisine(cuisine)
    }

    override suspend fun getCuisines(page: Int, limit: Int): List<Cuisine> {
        return cuisineGateway.getCuisines(page, limit)
    }

    override suspend fun deleteCuisine(id: String): Boolean {
        return cuisineGateway.deleteCuisine(id)
    }

    override suspend fun addCuisine(cuisine: Cuisine): Boolean {
        return cuisineGateway.addCuisine(cuisine)
    }
}