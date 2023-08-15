package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.usecase.validation.IRestaurantValidationUseCase
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND

interface IControlRestaurantsUseCase {
    suspend fun createRestaurant(restaurant: Restaurant): Restaurant
    suspend fun getAllRestaurants(page: Int, limit: Int): List<Restaurant>
    suspend fun updateRestaurant(restaurant: Restaurant): Restaurant
    suspend fun deleteRestaurant(restaurantId: String): Boolean
}

class ControlRestaurantsUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val basicValidation: IValidation,
    private val restaurantValidation: IRestaurantValidationUseCase
) : IControlRestaurantsUseCase {

    override suspend fun createRestaurant(restaurant: Restaurant): Restaurant {
        restaurantValidation. validateAddRestaurant(restaurant)
        return restaurantGateway.addRestaurant(restaurant)
    }

    override suspend fun getAllRestaurants(page: Int, limit: Int): List<Restaurant> {
        basicValidation.validatePagination(page, limit)
        return restaurantGateway.getRestaurants(page, limit)
    }

    override suspend fun updateRestaurant(restaurant: Restaurant): Restaurant {
        restaurantValidation.validateUpdateRestaurant(restaurant)
        checkIfRestaurantIsExist(restaurant.id)
        return restaurantGateway.updateRestaurant(restaurant)
    }

    override suspend fun deleteRestaurant(restaurantId: String): Boolean {
        checkIfRestaurantIsExist(restaurantId)
        return restaurantGateway.deleteRestaurant(restaurantId)
    }

    private suspend fun checkIfRestaurantIsExist(restaurantId: String) {
        if (!basicValidation.isValidId(restaurantId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        if (restaurantGateway.getRestaurant(restaurantId) == null) {
            throw MultiErrorException(listOf(NOT_FOUND))
        }
    }

}