package org.thechance.service_restaurant.usecase.category

import org.thechance.service_restaurant.api.usecases.DeleteCategoryUseCases
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

class DeleteCategoryUseCasesImp(private val restaurantGateway: RestaurantGateway) : DeleteCategoryUseCases {
    override suspend fun invoke(categoryId: String): Boolean {
        return restaurantGateway.deleteCategory(categoryId)
    }
}