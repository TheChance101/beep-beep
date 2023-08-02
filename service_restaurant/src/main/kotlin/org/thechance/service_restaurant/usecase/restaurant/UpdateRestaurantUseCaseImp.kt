package org.thechance.service_restaurant.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway
import org.thechance.service_restaurant.entity.Restaurant

@Single
class UpdateRestaurantUseCaseImp(private val restaurantGateway: RestaurantGateway) : UpdateRestaurantUseCase {
    override suspend fun invoke(restaurant: Restaurant): Boolean {
        checkUpdateValidation(restaurant)
        return restaurantGateway.updateRestaurant(restaurant)
    }

    private fun checkUpdateValidation(restaurant: Restaurant) {
    }
}