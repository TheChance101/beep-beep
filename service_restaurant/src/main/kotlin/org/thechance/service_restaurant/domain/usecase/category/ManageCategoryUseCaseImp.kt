package org.thechance.service_restaurant.domain.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.gateway.CategoryGateway
import org.thechance.service_restaurant.utils.DeleteCategoryException

@Single
class ManageCategoryUseCaseImp(private val categoryGateway: CategoryGateway) : ManageCategoryUseCase {
    override suspend fun getCategories(page: Int, limit: Int): List<Category> {
        return categoryGateway.getCategories(page, limit)
    }

    override suspend fun getCategoryDetails(categoryId: String): Category {
        return categoryGateway.getCategory(categoryId) ?: throw Throwable()
    }

    override suspend fun createCategory(category: Category): Boolean {
        return categoryGateway.addCategory(category)
    }

    override suspend fun updateCategory(category: Category): Boolean {
        return categoryGateway.updateCategory(category)
    }

    override suspend fun deleteCategory(categoryId: String): Boolean {
        return if (categoryGateway.deleteCategory(categoryId)) {
            true
        } else throw DeleteCategoryException
    }
}