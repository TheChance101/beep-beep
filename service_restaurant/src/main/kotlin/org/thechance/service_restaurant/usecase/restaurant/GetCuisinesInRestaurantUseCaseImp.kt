package org.thechance.service_restaurant.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway
import org.thechance.service_restaurant.entity.Cuisine

@Single
class GetCuisinesInRestaurantUseCaseImp(private val restaurantGateway: RestaurantGateway) : GetCuisinesInRestaurantUseCase {
    override suspend fun invoke(restaurantId: String): List<Cuisine> {
        return restaurantGateway.getCuisineInRestaurant(restaurantId)
    }
}