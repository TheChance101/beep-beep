package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.usecase.validation.RestaurantValidation
import org.thechance.service_restaurant.domain.usecase.validation.Validation
import org.thechance.service_restaurant.domain.utils.INVALID_ID
import org.thechance.service_restaurant.domain.utils.INVALID_ONE_OR_MORE_IDS
import org.thechance.service_restaurant.domain.utils.MultiErrorException
import org.thechance.service_restaurant.domain.utils.NOT_FOUND

interface IManageRestaurantDetailsUseCase {

    suspend fun getRestaurant(restaurantId: String): Restaurant
    suspend fun updateRestaurant(restaurant: Restaurant): Boolean
    suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category>
    suspend fun getAllCategories(page: Int, limit: Int): List<Category>
    suspend fun addCategoryToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean
    suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean

}

class ManageRestaurantDetailsUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val optionsGateway: IRestaurantOptionsGateway,
    private val restaurantValidation: RestaurantValidation,
    private val basicValidation: Validation
) : IManageRestaurantDetailsUseCase {
    override suspend fun getRestaurant(restaurantId: String): Restaurant {
        if (!basicValidation.isValidId(restaurantId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        return restaurantGateway.getRestaurant(restaurantId) ?: throw MultiErrorException(listOf(NOT_FOUND))
    }

    override suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category> {
        if (!basicValidation.isValidId(restaurantId)) throw MultiErrorException(listOf(INVALID_ID))
        restaurantGateway.getRestaurant(restaurantId) ?: throw MultiErrorException(listOf(NOT_FOUND))
        return optionsGateway.getCategoriesInRestaurant(restaurantId)
    }

    override suspend fun getAllCategories(page: Int, limit: Int): List<Category> {
        basicValidation. validatePagination(page, limit)
        return optionsGateway.getCategories(page, limit)
    }

    override suspend fun addCategoryToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        val validationErrors = mutableListOf<Int>()
        basicValidation.checkIsValidIds(restaurantId, categoryIds)
        restaurantGateway.getRestaurant(restaurantId) ?: validationErrors.add(NOT_FOUND)
        if (!optionsGateway.areCategoriesExisting(categoryIds)) {
            validationErrors.add(INVALID_ONE_OR_MORE_IDS)
        }
        return if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        } else {
            optionsGateway.addCategoriesToRestaurant(restaurantId, categoryIds)
        }
    }

    override suspend fun updateRestaurant(restaurant: Restaurant): Boolean {
        restaurantValidation.  validateUpdateRestaurantDetails(restaurant)
        val existRestaurant =
            restaurantGateway.getRestaurant(restaurant.id) ?: throw MultiErrorException(listOf(NOT_FOUND))
        restaurantValidation. validateRestaurantOwnership(existRestaurant, restaurant.ownerId)
        return restaurantGateway.updateRestaurant(restaurant)
    }

    override suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        basicValidation. checkIsValidIds(restaurantId, categoryIds)
        val validationErrors = mutableListOf<Int>()
        restaurantGateway.getRestaurant(restaurantId) ?: validationErrors.add(NOT_FOUND)
        if (!optionsGateway.areCategoriesExisting(categoryIds)) { validationErrors.add(INVALID_ONE_OR_MORE_IDS) }
        return if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        } else {
            restaurantGateway.deleteCategoriesInRestaurant(restaurantId, categoryIds)
        }
    }


}