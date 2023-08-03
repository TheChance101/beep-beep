package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.CategoryGateway

@Single
class AddRestaurantsToCategoryUseCasesImp(
    private val categoryGateway: CategoryGateway,
) : AddRestaurantsToCategoryUseCases {
    override suspend fun invoke(categoryId: String, restaurantIds: List<String>): Boolean {
        return categoryGateway.addRestaurantsToCategory(categoryId, restaurantIds)
    }
}