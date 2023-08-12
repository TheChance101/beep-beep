package org.thechance.service_restaurant.domain.usecase.validation

import org.koin.core.component.KoinComponent
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.Validation
import org.thechance.service_restaurant.domain.utils.exceptions.*

interface IRestaurantValidationUseCase {

    fun validateUpdateRestaurantDetails(restaurant: Restaurant)

    fun validateRestaurantOwnership(restaurant: Restaurant?, ownerId: String)
    fun validateAddRestaurant(restaurant: Restaurant)

    fun validateUpdateRestaurant(restaurant: Restaurant)

}

class RestaurantValidationUseCase(
    private val basicValidation: IValidation
) : IRestaurantValidationUseCase, KoinComponent {


    override fun validateAddRestaurant(restaurant: Restaurant) {
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

    override fun validateUpdateRestaurant(restaurant: Restaurant) {
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

    override fun validateUpdateRestaurantDetails(restaurant: Restaurant) {
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

    override fun validateRestaurantOwnership(restaurant: Restaurant?, ownerId: String) {
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

