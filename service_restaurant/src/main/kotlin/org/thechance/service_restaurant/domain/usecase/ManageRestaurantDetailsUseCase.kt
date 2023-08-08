package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.utils.*

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
) : IManageRestaurantDetailsUseCase {
    override suspend fun getRestaurant(restaurantId: String): Restaurant {
        return restaurantGateway.getRestaurant(restaurantId) ?: throw MultiErrorException(listOf(INVALID_ID))
    }

    override suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category> {
        checkIfRestaurantIsExist(restaurantId)?.let { throw MultiErrorException(listOf(it)) }
        return optionsGateway.getCategoriesInRestaurant(restaurantId)
    }

    override suspend fun getAllCategories(page: Int, limit: Int): List<Category> {
        validatePagination(page, limit)
        return optionsGateway.getCategories(page, limit)
    }

    override suspend fun addCategoryToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        val validationErrors = mutableListOf<Int>()
        checkIsValidIds(restaurantId, categoryIds)
        checkIfRestaurantIsExist(restaurantId)?.let { validationErrors.add(it) }
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
        val validationErrors = mutableListOf<Int>()
        getErrorsInUpdateRestaurantFields(restaurant)?.let { validationErrors.addAll(it) }
        val existRestaurant = restaurantGateway.getRestaurant(restaurant.id)
        validateRestaurantOwnership(existRestaurant, restaurant.ownerId)?.let { validationErrors.add(it) }
        return if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        } else {
            restaurantGateway.updateRestaurant(restaurant)
        }
    }

    override suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        val validationErrors = mutableListOf<Int>()
        checkIfRestaurantIsExist(restaurantId)?.let { validationErrors.add(it) }
        if (!optionsGateway.areCategoriesExisting(categoryIds)) {
            validationErrors.add(INVALID_ONE_OR_MORE_IDS)
        }
        return if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        } else {
            restaurantGateway.deleteCategoriesInRestaurant(restaurantId, categoryIds)
        }
    }

    private suspend fun checkIfRestaurantIsExist(restaurantId: String): Int? {
        return if (restaurantGateway.getRestaurant(restaurantId) == null) {
            NOT_FOUND
        } else {
            null
        }
    }

}