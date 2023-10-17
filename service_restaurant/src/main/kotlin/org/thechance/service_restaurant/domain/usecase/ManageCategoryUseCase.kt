package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.usecase.validation.ICategoryValidationUseCase
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_NAME
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND

interface IManageCategoryUseCase {
    suspend fun getCategories(): List<Category>

    suspend fun getCategoriesWithRestaurants(): List<Category>
    suspend fun createCategory(categoryName: String): Category
    suspend fun updateCategory(category: Category): Category
    suspend fun addRestaurantsToCategory(categoryId: String, restaurantIds: List<String>): Boolean
    suspend fun deleteCategory(categoryId: String): Boolean

}

class ManageCategoryUseCase(
    private val restaurantOptions: IRestaurantOptionsGateway,
    private val basicValidation: IValidation,
    private val categoryValidation: ICategoryValidationUseCase
) : IManageCategoryUseCase {

    override suspend fun getCategories(): List<Category> {
        return restaurantOptions.getCategories()
    }

    override suspend fun getCategoriesWithRestaurants(): List<Category> {
        return restaurantOptions.getCategoriesWithRestaurants()
    }

    override suspend fun createCategory(categoryName: String): Category {
        if (!basicValidation.isValidName(categoryName)) {
            throw MultiErrorException(listOf(INVALID_NAME))
        }
        return restaurantOptions.addCategory(categoryName)
    }

    override suspend fun updateCategory(category: Category): Category {
        categoryValidation.validationCategory(category)
        checkIfCategoryIsExist(category.id)
        return restaurantOptions.updateCategory(category)
    }

    override suspend fun addRestaurantsToCategory(categoryId: String, restaurantIds: List<String>): Boolean {
        return restaurantOptions.addRestaurantsToCategory(categoryId, restaurantIds)
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

}