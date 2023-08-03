package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.CategoryGateway
import org.thechance.service_restaurant.entity.Category

@Single
class UpdateCategoryUseCasesImp(
    private val categoryGateway: CategoryGateway
) : UpdateCategoryUseCases {
    override suspend fun invoke(category: Category): Boolean {
        return categoryGateway.updateCategory(category)
    }
}