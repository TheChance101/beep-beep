package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway

@Single
class DeleteRestaurantsInCategoryUseCasesImp(
    private val restaurantGateway: RestaurantGateway
) : DeleteRestaurantsInCategoryUseCases {
    override suspend fun invoke(categoryId: String, restaurantIds: List<String>): Boolean {
        return restaurantGateway.deleteRestaurantsInCategory(categoryId, restaurantIds)
    }
}