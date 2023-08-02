package org.thechance.service_restaurant.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.GetRestaurantsUseCase
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class GetRestaurantsUseCaseImp(private val restaurantGateway: RestaurantGateway) : GetRestaurantsUseCase {
    override suspend operator fun invoke(page: Int, limit: Int) = restaurantGateway.getRestaurants(page,limit)

}