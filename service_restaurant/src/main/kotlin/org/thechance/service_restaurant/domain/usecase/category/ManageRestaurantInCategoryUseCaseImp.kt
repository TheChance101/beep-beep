package org.thechance.service_restaurant.domain.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.CategoryGateway

@Single
class ManageRestaurantInCategoryUseCaseImp(private val categoryGateway: CategoryGateway) :
    ManageRestaurantInCategoryUseCase {
    override suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant> {
        return categoryGateway.getRestaurantsInCategory(categoryId)
    }

}