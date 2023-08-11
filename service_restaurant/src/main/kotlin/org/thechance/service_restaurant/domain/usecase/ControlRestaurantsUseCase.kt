package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.usecase.validation.RestaurantValidation
import org.thechance.service_restaurant.domain.usecase.validation.Validation
import org.thechance.service_restaurant.domain.utils.INVALID_ID
import org.thechance.service_restaurant.domain.utils.MultiErrorException
import org.thechance.service_restaurant.domain.utils.NOT_FOUND

interface IControlRestaurantsUseCase {
    suspend fun createRestaurant(restaurant: Restaurant): Boolean
    suspend fun getAllRestaurants(page: Int, limit: Int): List<Restaurant>
    suspend fun updateRestaurant(restaurant: Restaurant): Boolean
    suspend fun deleteRestaurant(restaurantId: String): Boolean
}

class ControlRestaurantsUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val restaurantValidation: RestaurantValidation,
    private val basicValidation: Validation
) : IControlRestaurantsUseCase {

    override suspend fun createRestaurant(restaurant: Restaurant): Boolean {
        restaurantValidation.validationRestaurant(restaurant)
        return restaurantGateway.addRestaurant(restaurant)
    }

    override suspend fun getAllRestaurants(page: Int, limit: Int): List<Restaurant> {
        basicValidation.validatePagination(page, limit)
        return restaurantGateway.getRestaurants(page, limit)
    }

    override suspend fun updateRestaurant(restaurant: Restaurant): Boolean {
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
            throw MultiErrorException(listOf( INVALID_ID))
        }
        if (restaurantGateway.getRestaurant(restaurantId) == null) {
            throw MultiErrorException(listOf( NOT_FOUND))
        }
    }
}