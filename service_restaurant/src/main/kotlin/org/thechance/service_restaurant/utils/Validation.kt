package org.thechance.service_restaurant.utils

import org.thechance.service_restaurant.domain.entity.Category
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

fun validatePagination(page: Int, limit: Int) {
    val validationErrors = mutableListOf<Int>()
    if (page < 1) {
        validationErrors.add(INVALID_PAGE)
    }

    if (limit !in 5..30) {
        validationErrors.add(INVALID_PAGE_LIMIT)
    }
    if (validationErrors.isNotEmpty()) {
        throw MultiErrorException(validationErrors)
    }
}

/* region require validation  */
fun isValidName(name: String?): Boolean {
    return name != null && name.matches(Regex("^[A-Za-z0-9\\s\\[\\]\\(\\)\\-.,&]{4,20}$"))
}

fun validatePhone(phone: String?): Boolean {
    return phone != null && phone.matches(Regex("\\d{3}\\d{3}\\d{4}"))
}

fun isValidId(id: String?): Boolean {
    return id != null && id.matches(Regex("^[0-9A-Fa-f]{24}$"))
}

internal fun isValidIds(ids: List<String>?): Boolean {
    return !ids.isNullOrEmpty() && ids.onEach { it.matches(Regex("^[0-9A-Fa-f]+$")) }.isNotEmpty()
}

fun validateTime(time: String?): Boolean {
    return time != null && time.matches(Regex("\\d{2}:\\d{2}"))
}

fun validateLatitude(latitude: Double): Boolean {
    return (latitude != -1.0) && (latitude in LATITUDE_MIN..LATITUDE_MAX)
}

fun validateLongitude(longitude: Double): Boolean {
    return (longitude != -1.0) && (longitude in LONGITUDE_MIN..LONGITUDE_MAX)
}

fun validateLocation(latitude: Double, longitude: Double): Boolean {
    return validateLatitude(latitude) && validateLongitude(longitude)
}

//endregion

//region optional validation
fun validatePriceLevel(priceLevel: String?): Boolean {
    return priceLevel.isNullOrBlank() || listOf("$", "$$", "$$$", "$$$$").contains(priceLevel)
}

fun validateRate(rate: Double): Boolean {
    return rate == NULL_DOUBLE || rate in 1.0..5.0
}

const val NULL_DOUBLE = -1.0

fun validateDescription(description: String): Boolean {
    return description.length <= DESCRIPTION_MAX_LENGTH
}


//endregion


fun getErrorsInUpdateRestaurantFields(restaurant: Restaurant): MutableList<Int>? {
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

        if (restaurant.description.isNotEmpty() && !(validateDescription(restaurant.description))) {
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

    return validationErrors.ifEmpty {
        null
    }
}

fun validateRestaurantOwnership(restaurant: Restaurant?, ownerId: String): Int? {
    return if (restaurant == null) {
        INVALID_ID
    } else if (restaurant.ownerId != ownerId) {
        INVALID_PROPERTY_RIGHTS
    } else {
        null
    }
}


const val DESCRIPTION_MAX_LENGTH = 255
const val LATITUDE_MIN = -90.0
const val LATITUDE_MAX = 90.0
const val LONGITUDE_MIN = -180.0
const val LONGITUDE_MAX = 180.0

