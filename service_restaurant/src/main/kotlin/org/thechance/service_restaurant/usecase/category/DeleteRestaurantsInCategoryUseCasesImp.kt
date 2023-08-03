package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.CategoryGateway

@Single
class DeleteRestaurantsInCategoryUseCasesImp(
    private val  categoryGateway: CategoryGateway
) : DeleteRestaurantsInCategoryUseCases {
    override suspend fun invoke(categoryId: String, restaurantIds: List<String>): Boolean {
        return categoryGateway.deleteRestaurantsInCategory(categoryId, restaurantIds)
    }
}