package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.utils.INVALID_NAME
import org.thechance.service_restaurant.domain.utils.InvalidParameterException
import org.thechance.service_restaurant.domain.utils.NOT_FOUND
import org.thechance.service_restaurant.domain.utils.ResourceNotFoundException
import org.thechance.service_restaurant.domain.utils.isValidName
import org.thechance.service_restaurant.domain.utils.validatePagination
import org.thechance.service_restaurant.utils.*

interface IManageCuisineUseCase {
    suspend fun getCuisines(page: Int, limit: Int): List<Cuisine>
    suspend fun addCuisine(cuisine: Cuisine): Boolean
    suspend fun updateCuisine(cuisine: Cuisine): Boolean
    suspend fun deleteCuisine(id: String): Boolean
}


class ManageCuisineUseCase(
    private val restaurantOptions: IRestaurantOptionsGateway
) : IManageCuisineUseCase {
    override suspend fun getCuisines(page: Int, limit: Int): List<Cuisine> {
        validatePagination(page, limit)
        return restaurantOptions.getCuisines(page, limit)
    }

    override suspend fun addCuisine(cuisine: Cuisine): Boolean {
        if (!isValidName(cuisine.name)) {
            throw InvalidParameterException(INVALID_NAME)
        }
        return restaurantOptions.addCuisine(cuisine)
    }

    override suspend fun deleteCuisine(id: String): Boolean {
        checkIfCuisineIsExist(id)
        return restaurantOptions.deleteCuisine(id)
    }

    override suspend fun updateCuisine(cuisine: Cuisine): Boolean {
        checkIfCuisineIsExist(cuisine.id)
        if (!isValidName(cuisine.name)) {
            throw InvalidParameterException(INVALID_NAME)
        }
        return restaurantOptions.updateCuisine(cuisine)
    }

    private suspend fun checkIfCuisineIsExist(cuisineId: String) {
        if (restaurantOptions.getCuisineById(cuisineId) == null) {
            throw ResourceNotFoundException(NOT_FOUND)
        }
    }
}