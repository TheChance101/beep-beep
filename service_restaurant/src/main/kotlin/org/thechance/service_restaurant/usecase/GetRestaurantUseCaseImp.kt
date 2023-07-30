package org.thechance.service_restaurant.usecase

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.GetRestaurantUseCase
import org.thechance.service_restaurant.entity.Restaurant
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class GetRestaurantUseCaseImp(private val restaurant: RestaurantGateway) : GetRestaurantUseCase {
    override suspend operator fun invoke() = restaurant.getRestaurant()

}