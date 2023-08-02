package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway
import org.thechance.service_restaurant.entity.Category

@Single
class GetCategoriesUseCasesImp(private val restaurantGateway: RestaurantGateway) : GetCategoriesUseCases {

    override suspend fun invoke(page: Int, limit: Int): List<Category> {
        return restaurantGateway.getCategories(page,limit)
    }
}