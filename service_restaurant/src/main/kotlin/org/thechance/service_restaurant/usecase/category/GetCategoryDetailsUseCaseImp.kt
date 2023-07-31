package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.GetCategoryDetailsUseCase
import org.thechance.service_restaurant.entity.Category
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class GetCategoryDetailsUseCaseImp(private val restaurantGateway: RestaurantGateway) : GetCategoryDetailsUseCase {
    override suspend fun invoke(categoryId: String): Category {
        return restaurantGateway.getCategory(categoryId) ?: throw Throwable()
    }
}