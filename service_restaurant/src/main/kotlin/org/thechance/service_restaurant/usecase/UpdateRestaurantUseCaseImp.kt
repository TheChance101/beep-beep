package org.thechance.service_restaurant.usecase

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.UpdateRestaurantUseCase
import org.thechance.service_restaurant.entity.Restaurant
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class UpdateRestaurantUseCaseImp(private val restaurantGateway: RestaurantGateway) : UpdateRestaurantUseCase {
    override suspend fun invoke(restaurant: Restaurant): Boolean {
        checkUpdateValidation(restaurant)
        return restaurantGateway.updateRestaurant(restaurant)
    }

    private fun checkUpdateValidation(restaurant: Restaurant) {
    }
}