package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway

@Single
class AddRestaurantsToCategoryUseCasesImp(
    private val restaurantGateway: RestaurantGateway
) : AddRestaurantsToCategoryUseCases {
    override suspend fun invoke(categoryId: String, restaurantIds: List<String>): Boolean {
        return restaurantGateway.addRestaurantsToCategory(categoryId, restaurantIds)
    }
}