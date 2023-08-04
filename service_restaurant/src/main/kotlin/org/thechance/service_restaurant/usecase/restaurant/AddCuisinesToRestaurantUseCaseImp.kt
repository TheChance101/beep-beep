package org.thechance.service_restaurant.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway

@Single
class AddCuisinesToRestaurantUseCaseImp(private val restaurantGateway: RestaurantGateway): AddCuisinesToRestaurantUseCase {
    override suspend fun invoke(restaurantId: String, cuisineIds: List<String>): Boolean {
        return restaurantGateway.addCuisineToRestaurant(restaurantId, cuisineIds)
    }
}