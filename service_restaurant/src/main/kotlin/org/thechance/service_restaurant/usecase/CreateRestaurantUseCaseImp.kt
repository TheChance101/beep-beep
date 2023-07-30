package org.thechance.service_restaurant.usecase

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.CreateRestaurantUseCase
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class CreateRestaurantUseCaseImp(private val restaurant: RestaurantGateway) : CreateRestaurantUseCase {
    override suspend operator fun invoke(name: String): Boolean {
        return restaurant.addRestaurant(name = name)
    }

}