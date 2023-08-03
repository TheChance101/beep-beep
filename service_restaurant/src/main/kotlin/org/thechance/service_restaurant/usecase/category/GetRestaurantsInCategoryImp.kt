package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.CategoryGateway
import org.thechance.service_restaurant.entity.Restaurant

@Single
class GetRestaurantsInCategoryImp(
    private val categoryGateway: CategoryGateway
) : GetRestaurantsInCategoryUseCases {
    override suspend fun invoke(categoryId: String): List<Restaurant> {
        return categoryGateway.getRestaurantsInCategory(categoryId)
    }
}