package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.Validation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_DESCRIPTION
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_LOCATION
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_NAME
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PHONE
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PRICE_LEVEL
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_RATE
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_TIME
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_UPDATE_PARAMETER
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
    private val basicValidation: IValidation
) : IControlRestaurantsUseCase {

    override suspend fun createRestaurant(restaurant: Restaurant): Restaurant {
        validateAddRestaurant(restaurant)
        return restaurantGateway.addRestaurant(restaurant)
    }

    override suspend fun getAllRestaurants(page: Int, limit: Int): List<Restaurant> {
        basicValidation.validatePagination(page, limit)
        return restaurantGateway.getRestaurants(page, limit)
    }

    override suspend fun updateRestaurant(restaurant: Restaurant): Restaurant {
        validateUpdateRestaurant(restaurant)
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

    private fun validateAddRestaurant(restaurant: Restaurant) {
        val validationErrors = mutableListOf<Int>()

        if (!(basicValidation.isValidName(restaurant.name))) {
            validationErrors.add(INVALID_NAME)
        }
        if (!basicValidation.isValidId(restaurant.ownerId)) {
            validationErrors.add(INVALID_ID)
        }
        if (!basicValidation.isValidLocation(
                restaurant.address.longitude,
                restaurant.address.latitude
            )
        ) {
            validationErrors.add(INVALID_LOCATION)
        }
        if (!(restaurant.description.isNullOrBlank() || basicValidation.isValidDescription(
                restaurant.description
            ))
        ) {
            validationErrors.add(INVALID_DESCRIPTION)
        }
        if (!(restaurant.priceLevel.isNullOrEmpty() || basicValidation.isValidatePriceLevel(
                restaurant.priceLevel
            ))
        ) {
            validationErrors.add(INVALID_PRICE_LEVEL)
        }
        if (restaurant.rate != null && restaurant.rate != Validation.NULL_DOUBLE && !basicValidation.isValidRate(
                restaurant.rate
            )
        ) {
            validationErrors.add(INVALID_RATE)
        }
        if (!basicValidation.isValidPhone(restaurant.phone)) {
            validationErrors.add(INVALID_PHONE)
        }
        if (!basicValidation.isValidTime(restaurant.closingTime) || !basicValidation.isValidTime(
                restaurant.openingTime
            )
        ) {
            validationErrors.add(INVALID_TIME)
        }
        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }

    private fun validateUpdateRestaurant(restaurant: Restaurant) {
        val validationErrors = mutableListOf<Int>()

        if (restaurant.id.isEmpty() || restaurant.ownerId.isEmpty()) {
            validationErrors.add(INVALID_REQUEST_PARAMETER)
        }

        if (!basicValidation.isValidId(restaurant.ownerId) || !basicValidation.isValidId(restaurant.id)) {
            validationErrors.add(INVALID_ID)
        }

        if (restaurant.name.isEmpty() &&
            restaurant.description.isNullOrEmpty() &&
            restaurant.priceLevel.isNullOrEmpty() &&
            restaurant.rate == null &&
            restaurant.phone.isEmpty() &&
            restaurant.closingTime.isEmpty() &&
            restaurant.openingTime.isEmpty() &&
            restaurant.address.latitude == Validation.NULL_DOUBLE &&
            restaurant.address.longitude == Validation.NULL_DOUBLE
        ) {
            validationErrors.add(INVALID_UPDATE_PARAMETER)
        } else {
            if (restaurant.name.isNotEmpty() && !(basicValidation.isValidName(restaurant.name))) {
                validationErrors.add(INVALID_NAME)
            }

            if (!(restaurant.description.isNullOrEmpty() ||
                        basicValidation.isValidDescription(restaurant.description))
            ) {
                validationErrors.add(INVALID_DESCRIPTION)
            }
            if (!(restaurant.priceLevel.isNullOrEmpty() || basicValidation.isValidatePriceLevel(
                    restaurant.priceLevel
                ))
            ) {
                validationErrors.add(INVALID_PRICE_LEVEL)
            }
            if (restaurant.rate != null && !basicValidation.isValidRate(restaurant.rate)) {
                validationErrors.add(INVALID_RATE)
            }
            if (restaurant.phone.isNotEmpty() && !basicValidation.isValidPhone(restaurant.phone)) {
                validationErrors.add(INVALID_PHONE)
            }
            if (restaurant.closingTime.isNotEmpty() && !basicValidation.isValidTime(restaurant.closingTime)) {
                validationErrors.add(INVALID_TIME)
            }
            if (restaurant.openingTime.isNotEmpty() && !basicValidation.isValidTime(restaurant.openingTime)) {
                validationErrors.add(INVALID_TIME)
            }
            if (restaurant.address.longitude != Validation.NULL_DOUBLE && !basicValidation.isValidLongitude(
                    restaurant.address.longitude
                )
            ) {
                validationErrors.add(INVALID_LOCATION)
            }
            if (restaurant.address.latitude != Validation.NULL_DOUBLE && !basicValidation.isValidLatitude(
                    restaurant.address.latitude
                )
            ) {
                validationErrors.add(INVALID_LOCATION)
            }
        }
        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }

}