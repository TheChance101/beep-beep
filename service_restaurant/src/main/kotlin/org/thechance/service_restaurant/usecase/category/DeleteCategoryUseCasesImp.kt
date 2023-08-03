package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway

@Single
class DeleteCategoryUseCasesImp(
    private val restaurantGateway: RestaurantGateway,
) : DeleteCategoryUseCases {
    override suspend fun invoke(categoryId: String): Boolean {
        return restaurantGateway.deleteCategory(categoryId)
    }
}