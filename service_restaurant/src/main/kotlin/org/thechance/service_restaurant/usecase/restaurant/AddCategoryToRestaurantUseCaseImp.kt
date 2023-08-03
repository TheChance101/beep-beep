package org.thechance.service_restaurant.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway

@Single
class AddCategoryToRestaurantUseCaseImp(
    private val restaurantGateway: RestaurantGateway
) : AddCategoryToRestaurantUseCase {
    override suspend fun invoke(restaurantId: String, categoryIds: List<String>): Boolean {
        return restaurantGateway.addCategoriesToRestaurant(restaurantId, categoryIds)
    }
}