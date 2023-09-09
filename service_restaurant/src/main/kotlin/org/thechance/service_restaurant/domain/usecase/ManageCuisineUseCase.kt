package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.exceptions.ALREADY_EXISTED
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_NAME
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND

interface IManageCuisineUseCase {
    suspend fun getCuisines(): List<Cuisine>
    suspend fun addCuisine(cuisine: Cuisine): Cuisine
    suspend fun updateCuisine(cuisine: Cuisine): Cuisine
    suspend fun deleteCuisine(id: String): Boolean
}

class ManageCuisineUseCase(
    private val restaurantOptions: IRestaurantOptionsGateway,
    private val basicValidation: IValidation
) : IManageCuisineUseCase {
    override suspend fun getCuisines(): List<Cuisine> {
        return restaurantOptions.getCuisines()
    }

    override suspend fun addCuisine(cuisine: Cuisine): Cuisine {
        if (!basicValidation.isValidName(cuisine.name)) {
            throw MultiErrorException(listOf(INVALID_NAME))
        }
       else if (isCuisineExisted(cuisine.name)){
            throw MultiErrorException(listOf(ALREADY_EXISTED))
        }
        return restaurantOptions.addCuisine(cuisine)
    }

    override suspend fun deleteCuisine(id: String): Boolean {
        checkIfCuisineIsExist(id)
        return restaurantOptions.deleteCuisine(id)
    }

    override suspend fun updateCuisine(cuisine: Cuisine): Cuisine {
        checkIfCuisineIsExist(cuisine.id)
        if (!basicValidation.isValidName(cuisine.name)) {
            throw MultiErrorException(listOf(INVALID_NAME))
        }
        return restaurantOptions.updateCuisine(cuisine)
    }

    private suspend fun checkIfCuisineIsExist(cuisineId: String) {
        if (restaurantOptions.getCuisineById(cuisineId) == null) {
            throw MultiErrorException(listOf(NOT_FOUND))
        }
    }

    private suspend fun isCuisineExisted(cuisineName: String): Boolean {
        return restaurantOptions.getCuisineByName(cuisineName) != null
    }
}