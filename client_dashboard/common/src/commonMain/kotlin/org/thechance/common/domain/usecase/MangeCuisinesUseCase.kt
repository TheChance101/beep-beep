package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Cuisine
import org.thechance.common.domain.getway.IRestaurantGateway

interface IMangeCuisinesUseCase {

    suspend fun getCuisines(): List<Cuisine>

    suspend fun createCuisine(cuisineName: String): Cuisine

    suspend fun deleteCuisine(cuisineId: String)

}

class MangeCuisinesUseCase(private val restaurantGateway: IRestaurantGateway) : IMangeCuisinesUseCase {

    override suspend fun getCuisines(): List<Cuisine> {
        return restaurantGateway.getCuisines()
    }

    override suspend fun createCuisine(cuisineName: String): Cuisine {
        return restaurantGateway.createCuisine(cuisineName)
    }

    override suspend fun deleteCuisine(cuisineId: String) {
         restaurantGateway.deleteCuisine(cuisineId)
    }

}