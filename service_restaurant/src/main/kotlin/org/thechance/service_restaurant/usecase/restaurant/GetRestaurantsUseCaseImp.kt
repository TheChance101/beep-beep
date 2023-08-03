package org.thechance.service_restaurant.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway

@Single
class GetRestaurantsUseCaseImp(private val restaurantGateway: RestaurantGateway) : GetRestaurantsUseCase {
    override suspend operator fun invoke(page: Int, limit: Int) = restaurantGateway.getRestaurants(page,limit)

}