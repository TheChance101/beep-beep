package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.CategoryGateway
import org.thechance.service_restaurant.utils.DeleteCategoryException

@Single
class DeleteCategoryUseCasesImp(
    private val categoryGateway: CategoryGateway
) : DeleteCategoryUseCases {
    override suspend fun invoke(categoryId: String): Boolean {
        return if (categoryGateway.deleteCategory(categoryId)){
            true
        }else throw DeleteCategoryException
    }

}