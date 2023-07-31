package org.thechance.service_restaurant.usecase

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.DeleteRestaurantUseCase
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class DeleteRestaurantUseCaseImp(private val restaurantGateway: RestaurantGateway) : DeleteRestaurantUseCase {
    override suspend fun invoke(restaurantId: String): Boolean {
        return if (restaurantId.isNotEmpty()) {
            restaurantGateway.deleteRestaurant(restaurantId)
        } else {
            throw Throwable()
        }
    }
}