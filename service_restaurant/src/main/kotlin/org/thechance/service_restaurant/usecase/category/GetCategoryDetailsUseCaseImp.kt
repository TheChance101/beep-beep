package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway
import org.thechance.service_restaurant.entity.Category

@Single
class GetCategoryDetailsUseCaseImp(private val restaurantGateway: RestaurantGateway) : GetCategoryDetailsUseCase {
    override suspend fun invoke(categoryId: String): Category {
        return restaurantGateway.getCategory(categoryId) ?: throw Throwable()
    }
}