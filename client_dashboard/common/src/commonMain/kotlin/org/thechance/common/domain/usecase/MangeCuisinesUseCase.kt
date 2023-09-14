package org.thechance.common.domain.usecase

import org.thechance.common.domain.getway.IRestaurantGateway

interface IMangeCuisinesUseCase {

    suspend fun getCuisines(): List<String>

    suspend fun createCuisine(cuisineName: String): String

    suspend fun deleteCuisine(cuisineName: String): String

}

class MangeCuisinesUseCase(private val restaurantGateway: IRestaurantGateway) : IMangeCuisinesUseCase {

    override suspend fun getCuisines(): List<String> {
        return restaurantGateway.getCuisines()
    }

    override suspend fun createCuisine(cuisineName: String): String {
        return restaurantGateway.createCuisine(cuisineName)
    }

    override suspend fun deleteCuisine(cuisineName: String): String {
        return restaurantGateway.deleteCuisine(cuisineName)
    }

}