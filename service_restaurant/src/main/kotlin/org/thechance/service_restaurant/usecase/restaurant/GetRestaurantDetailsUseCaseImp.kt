package org.thechance.service_restaurant.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway
import org.thechance.service_restaurant.entity.Restaurant

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