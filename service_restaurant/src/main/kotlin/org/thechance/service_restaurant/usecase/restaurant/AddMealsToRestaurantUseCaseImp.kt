package org.thechance.service_restaurant.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway

@Single
class AddMealsToRestaurantUseCaseImp(private val restaurantGateway: RestaurantGateway): AddMealsToRestaurantUseCase {
    override suspend fun invoke(restaurantId: String, mealIds: List<String>): Boolean {
        return restaurantGateway.addMealsToRestaurant(restaurantId, mealIds)
    }
}