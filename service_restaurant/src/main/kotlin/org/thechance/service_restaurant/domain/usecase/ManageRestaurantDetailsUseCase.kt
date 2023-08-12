package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.Validation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_DESCRIPTION
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_NAME
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ONE_OR_MORE_IDS
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PERMISSION_UPDATE_LOCATION
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PHONE
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PRICE_LEVEL
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PROPERTY_RIGHTS
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_RATE
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_TIME
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_UPDATE_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND

interface IManageRestaurantDetailsUseCase {

    suspend fun getRestaurant(restaurantId: String): Restaurant
    suspend fun updateRestaurant(restaurant: Restaurant): Restaurant
    suspend fun deleteCategoriesInRestaurant(
        restaurantId: String, categoryIds: List<String>
    ): Boolean

}

class ManageRestaurantDetailsUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val optionsGateway: IRestaurantOptionsGateway,
    private val basicValidation: IValidation
) : IManageRestaurantDetailsUseCase {
    override suspend fun getRestaurant(restaurantId: String): Restaurant {
        if (!basicValidation.isValidId(restaurantId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        return restaurantGateway.getRestaurant(restaurantId) ?: throw MultiErrorException(
            listOf(
                NOT_FOUND
            )
        )
    }

    override suspend fun updateRestaurant(restaurant: Restaurant): Restaurant {
        validateUpdateRestaurantDetails(restaurant)
        val existRestaurant =
            restaurantGateway.getRestaurant(restaurant.id) ?: throw MultiErrorException(
                listOf(
                    NOT_FOUND
                )
            )
        validateRestaurantOwnership(existRestaurant, restaurant.ownerId)
        return restaurantGateway.updateRestaurant(restaurant)
    }

    override suspend fun deleteCategoriesInRestaurant(
        restaurantId: String,
        categoryIds: List<String>
    ): Boolean {
        basicValidation.checkIsValidIds(restaurantId, categoryIds)
        val validationErrors = mutableListOf<Int>()
        restaurantGateway.getRestaurant(restaurantId) ?: validationErrors.add(NOT_FOUND)
        if (!optionsGateway.areCategoriesExisting(categoryIds)) {
            validationErrors.add(
                INVALID_ONE_OR_MORE_IDS
            )
        }
        return if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        } else {
            restaurantGateway.deleteCategoriesInRestaurant(restaurantId, categoryIds)
        }
    }

     private fun validateUpdateRestaurantDetails(restaurant: Restaurant) {
        val validationErrors = mutableListOf<Int>()

        if (!basicValidation.isValidId(restaurant.ownerId) || !basicValidation.isValidId(restaurant.id)) {
            validationErrors.add(INVALID_ID)
        }

        if (restaurant.address.longitude != Validation.NULL_DOUBLE || restaurant.address.latitude != Validation.NULL_DOUBLE) {
            validationErrors.add(INVALID_PERMISSION_UPDATE_LOCATION)
        }

        if (restaurant.name.isEmpty() &&
            restaurant.description.isNullOrEmpty() &&
            restaurant.priceLevel.isNullOrEmpty() &&
            restaurant.rate == Validation.NULL_DOUBLE &&
            restaurant.phone.isEmpty() &&
            restaurant.closingTime.isEmpty() &&
            restaurant.openingTime.isEmpty()
        ) {
            validationErrors.add(INVALID_UPDATE_PARAMETER)
        } else {
            if (restaurant.name.isNotEmpty() && !(basicValidation.isValidName(restaurant.name))) {
                validationErrors.add(INVALID_NAME)
            }

            if (!(restaurant.description.isNullOrEmpty() || basicValidation.isValidDescription(
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
            if (restaurant.rate == null || restaurant.rate != Validation.NULL_DOUBLE && !basicValidation.isValidRate(
                    restaurant.rate
                )
            ) {
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
        }
        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }

    private fun validateRestaurantOwnership(restaurant: Restaurant?, ownerId: String) {
        val error = if (restaurant == null) {
            INVALID_ID
        } else if (restaurant.ownerId != ownerId) {
            INVALID_PROPERTY_RIGHTS
        } else {
            null
        }

        if (error != null)
            throw MultiErrorException(listOf(error))
    }

}