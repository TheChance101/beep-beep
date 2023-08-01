package org.thechance.service_restaurant.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.GetRestaurantDetailsUseCase
import org.thechance.service_restaurant.entity.Restaurant
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class GetRestaurantDetailsUseCaseImp(private val restaurantGateway: RestaurantGateway) : GetRestaurantDetailsUseCase {
    override suspend operator fun invoke(id: String): Restaurant {
        return if (id.isNotEmpty()) {
            restaurantGateway.getRestaurant(id) ?: throw Throwable()
        } else {
            throw Throwable()
        }

    }
}