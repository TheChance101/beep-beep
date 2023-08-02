package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway
import org.thechance.service_restaurant.entity.Category

@Single
class CreateCategoryUseCasesImp(private val restaurantGateway: RestaurantGateway) : CreateCategoryUseCases {
    override suspend fun invoke(category: Category): Boolean {
        return restaurantGateway.addCategory(category)
    }
}