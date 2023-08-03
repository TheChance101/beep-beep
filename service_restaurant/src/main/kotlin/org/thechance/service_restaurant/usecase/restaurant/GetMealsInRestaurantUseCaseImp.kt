package org.thechance.service_restaurant.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway
import org.thechance.service_restaurant.entity.Meal

@Single
class GetMealsInRestaurantUseCaseImp(private val restaurantGateway: RestaurantGateway) : GetMealsInRestaurantUseCase {
    override suspend fun invoke(restaurantId: String): List<Meal> {
        return restaurantGateway.getMealsInRestaurant(restaurantId)
    }
}