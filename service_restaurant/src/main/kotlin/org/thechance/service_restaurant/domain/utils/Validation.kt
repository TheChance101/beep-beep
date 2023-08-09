package org.thechance.service_restaurant.domain.utils

import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.MealDetails
import org.thechance.service_restaurant.domain.entity.Restaurant

fun validationRestaurant(restaurant: Restaurant) {
    val validationErrors = mutableListOf<Int>()

    if (!(isValidName(restaurant.name))) {
        validationErrors.add(INVALID_NAME)
    }
    if (!isValidId(restaurant.ownerId)) {
        validationErrors.add(INVALID_ID)
    }
    if (!validateLocation(restaurant.address.longitude, restaurant.address.latitude)) {
        validationErrors.add(INVALID_LOCATION)
    }
    if (!(validateDescription(restaurant.description))) {
        validationErrors.add(INVALID_DESCRIPTION)
    }
    if (!validatePriceLevel(restaurant.priceLevel)) {
        validationErrors.add(INVALID_PRICE_LEVEL)
    }
    if (!validateRate(restaurant.rate)) {
        validationErrors.add(INVALID_RATE)
    }
    if (!validatePhone(restaurant.phone)) {
        validationErrors.add(INVALID_PHONE)
    }
    if (!validateTime(restaurant.closingTime) || !validateTime(restaurant.openingTime)) {
        validationErrors.add(INVALID_TIME)
    }
    if (validationErrors.isNotEmpty()) {
        throw MultiErrorException(validationErrors)
    }
}


fun validationCategory(category: Category) {
    val validationErrors = mutableListOf<Int>()

    if (!isValidId(category.id)) {
        validationErrors.add(INVALID_ID)
    }
    if (!isValidName(category.name)) {
        validationErrors.add(INVALID_NAME)
    }
    if (validationErrors.isNotEmpty()) {
        throw MultiErrorException(validationErrors)
    }
}

fun checkIsValidIds(id: String, listIds: List<String>) {
    val validationErrors = mutableListOf<Int>()

    if (!isValidId(id)) {
        validationErrors.add(INVALID_ID)
    }
    if (!isValidIds(listIds)) {
        validationErrors.add(INVALID_ID)
    }
    if (validationErrors.isNotEmpty()) {
        throw MultiErrorException(validationErrors)
    }
}



fun validateUpdateRestaurant(restaurant: Restaurant) {
    val validationErrors = mutableListOf<Int>()

    if (!isValidId(restaurant.ownerId) || !isValidId(restaurant.id)) {
        validationErrors.add(INVALID_ID)
    }

    if (restaurant.address.longitude != NULL_DOUBLE || restaurant.address.latitude != NULL_DOUBLE) {
        validationErrors.add(INVALID_PERMISSION_UPDATE_LOCATION)
    }

    if (restaurant.name.isEmpty() &&
        restaurant.description.isEmpty() &&
        restaurant.priceLevel.isEmpty() &&
        restaurant.rate == NULL_DOUBLE &&
        restaurant.phone.isEmpty() &&
        restaurant.closingTime.isEmpty() &&
        restaurant.openingTime.isEmpty()
    ) {
        validationErrors.add(INVALID_UPDATE_PARAMETER)
    } else {
        if (restaurant.name.isNotEmpty() && !(isValidName(restaurant.name))) {
            validationErrors.add(INVALID_NAME)
        }

        if (!(validateDescription(restaurant.description))) {
            validationErrors.add(INVALID_DESCRIPTION)
        }
        if (restaurant.priceLevel.isNotEmpty() && !validatePriceLevel(restaurant.priceLevel)) {
            validationErrors.add(INVALID_PRICE_LEVEL)
        }
        if (restaurant.rate != -1.0 && !validateRate(restaurant.rate)) {
            validationErrors.add(INVALID_RATE)
        }
        if (restaurant.phone.isNotEmpty() && !validatePhone(restaurant.phone)) {
            validationErrors.add(INVALID_PHONE)
        }
        if (restaurant.closingTime.isNotEmpty() && !validateTime(restaurant.closingTime)) {
            validationErrors.add(INVALID_TIME)
        }
        if (restaurant.openingTime.isNotEmpty() && !validateTime(restaurant.openingTime)) {
            validationErrors.add(INVALID_TIME)
        }
    }
    if (validationErrors.isNotEmpty()) {
        throw MultiErrorException(validationErrors)
    }
}

fun validateRestaurantOwnership(restaurant: Restaurant?, ownerId: String) {
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

fun validateAddMeal(meal: MealDetails) {
    val validationErrors = mutableListOf<Int>()

    if (!(isValidName(meal.name))) {
        validationErrors.add(INVALID_NAME)
    }
    if (!isValidId(meal.restaurantId)) {
        validationErrors.add(INVALID_ID)
    }

    if (meal.description.isEmpty() || !validateDescription(meal.description)) {
        validationErrors.add(INVALID_DESCRIPTION)
    }

    if (!validatePrice(meal.price)) {
        validationErrors.add(INVALID_PRICE)
    }

    if (meal.cuisines.isEmpty()) {
        validationErrors.add(INVALID_REQUEST_PARAMETER)
    }

    meal.cuisines.forEach {
        if (!isValidId(it.id)) {
            validationErrors.add(INVALID_ONE_OR_MORE_IDS)
            return@forEach
        }
    }

    if (validationErrors.isNotEmpty()) {
        throw MultiErrorException(validationErrors)
    }
}

fun validateUpdateMeal(meal: MealDetails) {
    val validationErrors = mutableListOf<Int>()

    if (meal.id.isEmpty() || meal.restaurantId.isEmpty()) {
        validationErrors.add(INVALID_REQUEST_PARAMETER)
    }

    if (!isValidId(meal.id) || !isValidId(meal.restaurantId)) {
        validationErrors.add(INVALID_ID)
    }

    if (meal.name.isEmpty() && meal.cuisines.isEmpty() && meal.price == NULL_DOUBLE && meal.description.isEmpty()) {
        validationErrors.add(INVALID_UPDATE_PARAMETER)
    } else {
        if (!isValidName(meal.name)) {
            validationErrors.add(INVALID_NAME)
        }


        if (!validateDescription(meal.description)) {
            validationErrors.add(INVALID_DESCRIPTION)
        }

        if (!validatePrice(meal.price)) {
            validationErrors.add(INVALID_PRICE)
        }

        meal.cuisines.forEach {
            if (!isValidId(it.id)) {
                validationErrors.add(INVALID_ID)
                return@forEach
            }
        }


        meal.cuisines.forEach {
            if (!isValidId(it.id)) {
                validationErrors.add(INVALID_ONE_OR_MORE_IDS)
                return@forEach
            }
        }
    }



    if (validationErrors.isNotEmpty()) {
        throw MultiErrorException(validationErrors)
    }
}

const val DESCRIPTION_MAX_LENGTH = 255
const val LATITUDE_MIN = -90.0
const val LATITUDE_MAX = 90.0
const val LONGITUDE_MIN = -180.0
const val LONGITUDE_MAX = 180.0
const val NULL_DOUBLE = -1.0

