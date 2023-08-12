package org.thechance.service_restaurant.domain.usecase.validation

import org.thechance.service_restaurant.domain.utils.INVALID_ID
import org.thechance.service_restaurant.domain.utils.INVALID_PAGE
import org.thechance.service_restaurant.domain.utils.INVALID_PAGE_LIMIT
import org.thechance.service_restaurant.domain.utils.MultiErrorException

class Validation {
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


    fun isValidName(name: String?): Boolean {
        return name != null && name.matches(Regex("^[A-Za-z0-9\\s\\[\\]\\(\\)\\-.,&]{4,25}$"))
    }

    fun isValidPhone(phone: String?): Boolean {
        return phone != null && phone.matches(Regex("\\d{3}\\d{3}\\d{4}"))
    }

    fun isValidId(id: String?): Boolean {
        return id != null && id.matches(Regex("[0-9a-fA-F]{24}"))
    }

    internal fun isValidIds(ids: List<String>?): Boolean {
        return !ids.isNullOrEmpty() && ids.all { isValidId(it) }
    }

    fun isValidatePriceLevel(priceLevel: String): Boolean {
        return listOf("$", "$$", "$$$", "$$$$").contains(priceLevel)
    }

    fun isValidRate(rate: Double): Boolean {
        return rate in 0.0..5.0
    }

    fun isValidPrice(price: Double): Boolean {
        return price in 1.0..1000.0
    }

    fun isValidDescription(description: String): Boolean {
        return description.length in DESCRIPTION_MIN_LENGTH..DESCRIPTION_MAX_LENGTH
    }


    fun isValidLatitude(latitude: Double): Boolean {
        return (latitude != -1.0) && (latitude in LATITUDE_MIN..LATITUDE_MAX)
    }

    fun isValidLongitude(longitude: Double): Boolean {
        return (longitude != -1.0) && (longitude in LONGITUDE_MIN..LONGITUDE_MAX)
    }

    fun isValidLocation(latitude: Double, longitude: Double): Boolean {
        return isValidLatitude(latitude) && isValidLongitude(longitude)
    }

    fun isValidTime(time: String?): Boolean {
        return time != null && time.matches(Regex("\\d{2}:\\d{2}"))
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


    companion object {
        const val DESCRIPTION_MAX_LENGTH = 255
        const val DESCRIPTION_MIN_LENGTH = 20
        const val LATITUDE_MIN = -90.0
        const val LATITUDE_MAX = 90.0
        const val LONGITUDE_MIN = -180.0
        const val LONGITUDE_MAX = 180.0
        const val NULL_DOUBLE = -1.0
        const val MAX_CUISINE = 3
    }


}