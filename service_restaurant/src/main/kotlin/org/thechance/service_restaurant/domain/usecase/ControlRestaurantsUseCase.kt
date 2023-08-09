package org.thechance.service_restaurant.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.utils.INVALID_ID
import org.thechance.service_restaurant.domain.utils.InvalidParameterException
import org.thechance.service_restaurant.domain.utils.NOT_FOUND
import org.thechance.service_restaurant.domain.utils.ResourceNotFoundException
import org.thechance.service_restaurant.domain.utils.isValidId
import org.thechance.service_restaurant.domain.utils.validatePagination
import org.thechance.service_restaurant.domain.utils.validationRestaurant
import org.thechance.service_restaurant.utils.*

interface IControlRestaurantsUseCase {
    suspend fun createRestaurant(restaurant: Restaurant): Boolean
    suspend fun getAllRestaurants(page: Int, limit: Int): List<Restaurant>
    suspend fun deleteRestaurant(restaurantId: String): Boolean
}

@Single
class ControlRestaurantsUseCase(
    private val restaurantGateway: IRestaurantGateway,
) : IControlRestaurantsUseCase {

    override suspend fun createRestaurant(restaurant: Restaurant): Boolean {
        validationRestaurant(restaurant)
        return restaurantGateway.addRestaurant(restaurant)
    }

    override suspend fun getAllRestaurants(page: Int, limit: Int): List<Restaurant> {
        validatePagination(page,limit)
        return restaurantGateway.getRestaurants(page,limit)
    }

    override suspend fun deleteRestaurant(restaurantId: String): Boolean {
        checkIfRestaurantIsExist(restaurantId)
        return restaurantGateway.deleteRestaurant(restaurantId)
    }

    private suspend fun checkIfRestaurantIsExist(restaurantId: String) {
        if (!isValidId(restaurantId)) {
            throw InvalidParameterException(INVALID_ID)
        }
        if (restaurantGateway.getRestaurant(restaurantId) == null) {
            throw ResourceNotFoundException(NOT_FOUND)
        }
    }
}