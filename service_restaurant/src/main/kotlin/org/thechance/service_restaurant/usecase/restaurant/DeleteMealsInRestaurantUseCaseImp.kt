package org.thechance.service_restaurant.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway

@Single
class DeleteMealsInRestaurantUseCaseImp(private val restaurantGateway: RestaurantGateway):DeleteMealsInRestaurantUseCase {
    override suspend fun invoke(restaurantId: String, mealIds: List<String>): Boolean {
       return restaurantGateway.deleteMealsInRestaurant(restaurantId, mealIds)
    }
}