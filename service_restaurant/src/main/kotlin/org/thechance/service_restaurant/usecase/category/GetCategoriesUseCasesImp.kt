package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.GetCategoriesUseCases
import org.thechance.service_restaurant.entity.Category
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class GetCategoriesUseCasesImp(private val restaurantGateway: RestaurantGateway) : GetCategoriesUseCases {
    override suspend fun invoke(): List<Category> {
        return restaurantGateway.getCategories()
    }
}