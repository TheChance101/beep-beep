package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.Validation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_NAME
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND

interface IManageCategoryUseCase {
    suspend fun getCategories(page: Int, limit: Int): List<Category>
    suspend fun createCategory(category: Category): Category
    suspend fun updateCategory(category: Category): Category
    suspend fun deleteCategory(categoryId: String): Boolean
}

class ManageCategoryUseCase(
    private val restaurantOptions: IRestaurantOptionsGateway,
    private val basicValidation: IValidation
) : IManageCategoryUseCase {
    override suspend fun getCategories(page: Int, limit: Int): List<Category> {
        basicValidation.validatePagination(page, limit)
        return restaurantOptions.getCategories(page, limit)
    }

    override suspend fun createCategory(category: Category): Category {
        if (!basicValidation.isValidName(category.name)) {
            throw MultiErrorException(listOf(INVALID_NAME))
        }
        return restaurantOptions.addCategory(category)
    }

    override suspend fun updateCategory(category: Category): Category {
        validationCategory(category)
        checkIfCategoryIsExist(category.id)
        return restaurantOptions.updateCategory(category)
    }

    override suspend fun deleteCategory(categoryId: String): Boolean {
        checkIfCategoryIsExist(categoryId)
        return restaurantOptions.deleteCategory(categoryId)
    }

    private suspend fun checkIfCategoryIsExist(categoryId: String) {
        if (!basicValidation.isValidId(categoryId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        if (restaurantOptions.getCategory(categoryId) == null) {
            throw MultiErrorException(listOf(NOT_FOUND))
        }
    }

    private fun validationCategory(category: Category) {
        val validationErrors = mutableListOf<Int>()

        if (!basicValidation.isValidId(category.id)) {
            validationErrors.add(INVALID_ID)
        }
        if (!basicValidation.isValidName(category.name)) {
            validationErrors.add(INVALID_NAME)
        }
        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }

}