package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Cuisine
import org.thechance.common.domain.getway.IRestaurantGateway

interface IMangeCuisinesUseCase {

    suspend fun createCuisine(cuisineName: String,image:ByteArray): Cuisine

    suspend fun deleteCuisine(cuisineId: String)

}

class MangeCuisinesUseCase(
    private val restaurantGateway: IRestaurantGateway
) : IMangeCuisinesUseCase {

    override suspend fun createCuisine(cuisineName: String,image:ByteArray): Cuisine {
        return restaurantGateway.createCuisine(cuisineName,image)
    }

    override suspend fun deleteCuisine(cuisineId: String) {
         restaurantGateway.deleteCuisine(cuisineId)
    }

}