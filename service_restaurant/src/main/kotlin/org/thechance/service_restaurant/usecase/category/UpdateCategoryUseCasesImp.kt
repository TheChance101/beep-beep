package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway
import org.thechance.service_restaurant.entity.Category

@Single
class UpdateCategoryUseCasesImp(private val restaurantGateway: RestaurantGateway) : UpdateCategoryUseCases {
    override suspend fun invoke(category: Category): Boolean {
        return restaurantGateway.updateCategory(category)
    }
}