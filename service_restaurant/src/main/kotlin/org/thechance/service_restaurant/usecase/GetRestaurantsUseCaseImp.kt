package org.thechance.service_restaurant.usecase

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.GetRestaurantsUseCase
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class GetRestaurantsUseCaseImp(private val restaurant: RestaurantGateway) : GetRestaurantsUseCase {
    override suspend operator fun invoke() = restaurant.getRestaurants()

}