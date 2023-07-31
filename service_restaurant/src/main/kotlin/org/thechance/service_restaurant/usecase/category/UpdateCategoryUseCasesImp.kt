package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.UpdateCategoryUseCases
import org.thechance.service_restaurant.entity.Category
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class UpdateCategoryUseCasesImp(private val restaurantGateway: RestaurantGateway) : UpdateCategoryUseCases {
    override suspend fun invoke(category: Category): Boolean {
        return restaurantGateway.updateCategory(category)
    }
}