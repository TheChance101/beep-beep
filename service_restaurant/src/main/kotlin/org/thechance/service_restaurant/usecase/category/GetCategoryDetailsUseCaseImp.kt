package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.CategoryGateway
import org.thechance.service_restaurant.entity.Category

@Single
class GetCategoryDetailsUseCaseImp(
    private val categoryGateway: CategoryGateway
) : GetCategoryDetailsUseCase {
    override suspend fun invoke(categoryId: String): Category {
        return categoryGateway.getCategory(categoryId) ?: throw Throwable()
    }
}