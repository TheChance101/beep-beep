package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.utils.*

interface IControlRestaurant {
    suspend fun createRestaurant(restaurant: Restaurant): Boolean
    suspend fun getAllRestaurants(page: Int, limits: Int): List<Restaurant>
    suspend fun deleteRestaurant(restaurantId: String): Boolean
}

class ControlRestaurant(
    private val restaurantGateway: IRestaurantGateway,
) : IControlRestaurant {

    override suspend fun createRestaurant(restaurant: Restaurant): Boolean {
        validationRestaurant(restaurant)
        return restaurantGateway.addRestaurant(restaurant)
    }

    override suspend fun getAllRestaurants(page: Int, limits: Int): List<Restaurant> {
        return restaurantGateway.getRestaurants(page,limits)
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