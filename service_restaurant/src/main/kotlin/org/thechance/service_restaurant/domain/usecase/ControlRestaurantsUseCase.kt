package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.entity.RestaurantOptions
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.usecase.validation.IRestaurantValidationUseCase
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND
import org.thechance.service_restaurant.domain.utils.getCurrencyForLocation

interface IControlRestaurantsUseCase {
    suspend fun createRestaurant(restaurant: Restaurant): Restaurant
    suspend fun getAllRestaurants(restaurantOptions: RestaurantOptions): List<Restaurant>
    suspend fun updateRestaurant(restaurant: Restaurant): Restaurant
    suspend fun deleteRestaurant(restaurantId: String): Boolean
    suspend fun getTotalNumberOfRestaurant(): Long
    suspend fun deleteRestaurantsByOwnerId(ownerId: String): Boolean
}

class ControlRestaurantsUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val basicValidation: IValidation,
    private val restaurantValidation: IRestaurantValidationUseCase
) : IControlRestaurantsUseCase {

    override suspend fun createRestaurant(restaurant: Restaurant): Restaurant {
        val currency = getCurrencyForLocation(restaurant.location)
        val newRestaurant = restaurant.copy(currency = currency)
        restaurantValidation.validateAddRestaurant(newRestaurant)
        return restaurantGateway.addRestaurant(newRestaurant)
    }

    override suspend fun getAllRestaurants(restaurantOptions: RestaurantOptions): List<Restaurant> {
        basicValidation.validatePagination(restaurantOptions.page, restaurantOptions.limit)
        restaurantOptions.priceLevel?.let { basicValidation.isValidatePriceLevel(it) }
        restaurantOptions.rating?.let { basicValidation.isValidRate(it) }
        return restaurantGateway.getRestaurants(restaurantOptions)
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

    override suspend fun getTotalNumberOfRestaurant(): Long {
        return restaurantGateway.getTotalNumberOfRestaurant()
    }

    override suspend fun deleteRestaurantsByOwnerId(ownerId: String): Boolean {
        return restaurantGateway.deleteRestaurantsByOwnerId(ownerId)
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