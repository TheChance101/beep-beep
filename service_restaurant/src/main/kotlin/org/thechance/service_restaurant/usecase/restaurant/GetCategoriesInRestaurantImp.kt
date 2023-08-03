package org.thechance.service_restaurant.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway
import org.thechance.service_restaurant.entity.Category

@Single
class GetCategoriesInRestaurantImp(private val restaurantGateway: RestaurantGateway) : GetCategoriesInRestaurantUseCase {
    override suspend fun invoke(restaurantId: String): List<Category> {
        return restaurantGateway.getCategoriesInRestaurant(restaurantId)
    }
}