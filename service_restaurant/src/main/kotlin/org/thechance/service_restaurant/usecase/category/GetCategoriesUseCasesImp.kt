package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.CategoryGateway
import org.thechance.service_restaurant.entity.Category

@Single
class GetCategoriesUseCasesImp(
    private val  categoryGateway: CategoryGateway
) : GetCategoriesUseCases {

    override suspend fun invoke(page: Int, limit: Int): List<Category> {
        return categoryGateway.getCategories(page, limit)
    }
}