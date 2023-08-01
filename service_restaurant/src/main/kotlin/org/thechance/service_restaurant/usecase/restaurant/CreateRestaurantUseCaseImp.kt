package org.thechance.service_restaurant.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.CreateRestaurantUseCase
import org.thechance.service_restaurant.entity.Restaurant
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class CreateRestaurantUseCaseImp(private val restaurantGateway: RestaurantGateway) : CreateRestaurantUseCase {
    override suspend operator fun invoke(restaurant: Restaurant): Boolean {
        checkValidation(restaurant)
        return restaurantGateway.addRestaurant(restaurant)
    }

    private fun checkValidation(restaurant: Restaurant) {

    }

}