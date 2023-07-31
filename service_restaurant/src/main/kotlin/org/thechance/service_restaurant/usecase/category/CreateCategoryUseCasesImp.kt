package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.CreateCategoryUseCases
import org.thechance.service_restaurant.entity.Category
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class CreateCategoryUseCasesImp(private val restaurantGateway: RestaurantGateway) : CreateCategoryUseCases {
    override suspend fun invoke(category: Category): Boolean {
        return restaurantGateway.addCategory(category)
    }
}