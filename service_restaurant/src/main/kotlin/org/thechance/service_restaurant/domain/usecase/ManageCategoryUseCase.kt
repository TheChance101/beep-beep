package org.thechance.service_restaurant.domain.usecase

import io.netty.channel.unix.Limits
import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.utils.*

interface IManageCategoryUseCase {
    suspend fun getCategories(page: Int, limits: Int): List<Category>
    suspend fun createCategory(category: Category): Boolean
    suspend fun updateCategory(category: Category): Boolean
    suspend fun deleteCategory(categoryId: String): Boolean
}

@Single
class ManageCategoryUseCase(
    private val restaurantOptions: IRestaurantOptionsGateway
) : IManageCategoryUseCase {
    override suspend fun getCategories(page: Int, limits: Int): List<Category> {
        return restaurantOptions.getCategories(page, limits)
    }

    override suspend fun createCategory(category: Category): Boolean {
        if (!isValidName(category.name)) {
            throw InvalidParameterException(INVALID_NAME)
        }
        return restaurantOptions.addCategory(category)
    }

    override suspend fun updateCategory(category: Category): Boolean {
        validationCategory(category)
        checkIfCategoryIsExist(category.id)
        return restaurantOptions.updateCategory(category)
    }

    override suspend fun deleteCategory(categoryId: String): Boolean {
        checkIfCategoryIsExist(categoryId)
        return restaurantOptions.deleteCategory(categoryId)
    }

    private suspend fun checkIfCategoryIsExist(categoryId: String) {
        if (!isValidId(categoryId)) {
            throw InvalidParameterException(INVALID_ID)
        }
        if (restaurantOptions.getCategory(categoryId) == null) {
            throw ResourceNotFoundException(NOT_FOUND)
        }
    }
}