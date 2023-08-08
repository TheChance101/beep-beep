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
        validateRestaurantOwnership(restaurant.id, restaurant.ownerId)?.let { validationErrors.add(it) }
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
        if (!isValidId(restaurantId)) {
            return INVALID_ID
        }
        if (restaurantGateway.getRestaurant(restaurantId) == null) {
            return NOT_FOUND
        }
        return null
    }

    private fun getErrorsInUpdateRestaurantFields(restaurant: Restaurant): MutableList<Int>? {
        val validationErrors = mutableListOf<Int>()

        if (!isValidId(restaurant.ownerId) || !isValidId(restaurant.id)) {
            validationErrors.add(INVALID_ID)
        }

        if (restaurant.address.longitude != -1.0 || restaurant.address.latitude != -1.0) {
            validationErrors.add(INVALID_PERMISSION_UPDATE_LOCATION)
        }

        if (restaurant.name.isEmpty() &&
            restaurant.description.isEmpty() &&
            restaurant.priceLevel.isEmpty() &&
            restaurant.rate == -1.0 &&
            restaurant.phone.isEmpty() &&
            restaurant.closingTime.isEmpty() &&
            restaurant.openingTime.isEmpty()
        ) {
            validationErrors.add(INVALID_UPDATE_PARAMETER)
        } else {
            if (restaurant.name.isNotEmpty() && !(isValidName(restaurant.name))) {
                validationErrors.add(INVALID_NAME)
            }

            if (restaurant.description.isNotEmpty() && !(validateDescription(restaurant.description))) {
                validationErrors.add(INVALID_DESCRIPTION)
            }
            if (restaurant.priceLevel.isNotEmpty() && !validatePriceLevel(restaurant.priceLevel)) {
                validationErrors.add(INVALID_PRICE_LEVEL)
            }
            if (restaurant.rate != -1.0 && !validateRate(restaurant.rate)) {
                validationErrors.add(INVALID_RATE)
            }
            if (restaurant.phone.isNotEmpty() && !validatePhone(restaurant.phone)) {
                validationErrors.add(INVALID_PHONE)
            }
            if (restaurant.closingTime.isNotEmpty() && !validateTime(restaurant.closingTime)) {
                validationErrors.add(INVALID_TIME)
            }
            if (restaurant.openingTime.isNotEmpty() && !validateTime(restaurant.openingTime)) {
                validationErrors.add(INVALID_TIME)
            }
        }

        return if (validationErrors.isNotEmpty()) {
            validationErrors
        } else {
            null
        }
    }

    private suspend fun validateRestaurantOwnership(restaurantId: String, ownerId: String): Int? {
        val restaurant = restaurantGateway.getRestaurant(restaurantId)
        return if (restaurant == null) {
            INVALID_ID
        } else if (restaurant.ownerId != ownerId) {
            INVALID_PROPERTY_RIGHTS
        } else {
            null
        }

    }
}