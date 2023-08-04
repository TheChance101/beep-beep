package org.thechance.service_restaurant.domain.usecase.category

import org.thechance.service_restaurant.domain.entity.Category

interface ManageCategoryUseCase {
    suspend fun getCategories(page: Int, limit: Int): List<Category>

    suspend fun getCategoryDetails(categoryId: String): Category

}