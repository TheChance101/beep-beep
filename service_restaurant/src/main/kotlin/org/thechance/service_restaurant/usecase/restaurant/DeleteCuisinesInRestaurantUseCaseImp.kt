package org.thechance.service_restaurant.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway

@Single
class DeleteCuisinesInRestaurantUseCaseImp(private val restaurantGateway: RestaurantGateway):DeleteCuisinesInRestaurantUseCase {
    override suspend fun invoke(restaurantId: String, cuisineIds: List<String>): Boolean {
       return restaurantGateway.deleteCuisinesInRestaurant(restaurantId, cuisineIds)
    }
}