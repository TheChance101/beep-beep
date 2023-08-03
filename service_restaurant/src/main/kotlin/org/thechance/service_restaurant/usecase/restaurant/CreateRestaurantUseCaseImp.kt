package org.thechance.service_restaurant.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway
import org.thechance.service_restaurant.entity.Restaurant

@Single
class CreateRestaurantUseCaseImp(private val restaurantGateway: RestaurantGateway) : CreateRestaurantUseCase {
    override suspend operator fun invoke(restaurant: Restaurant): Boolean {
        checkValidation(restaurant)
        return restaurantGateway.addRestaurant(restaurant)
    }

    private fun checkValidation(restaurant: Restaurant) {

    }

}