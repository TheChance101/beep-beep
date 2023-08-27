package org.thechance.service_restaurant.domain.utils

import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PAGE
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PAGE_LIMIT
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException

interface IValidation {
    fun validatePagination(page: Int, limit: Int)
    fun isValidName(name: String?): Boolean
    fun isValidPhone(phone: String?): Boolean
    fun isValidId(id: String?): Boolean
    fun isValidatePriceLevel(priceLevel: String): Boolean
    fun isValidRate(rate: Double): Boolean
    fun isValidPrice(price: Double): Boolean
    fun isValidDescription(description: String): Boolean
    fun isValidLatitude(latitude: Double): Boolean
    fun isValidLongitude(longitude: Double): Boolean
    fun isValidLocation(latitude: Double, longitude: Double): Boolean
    fun isValidTime(time: String?): Boolean
    fun checkIsValidIds(id: String, listIds: List<String>)

    fun isValidAddress(address: String): Boolean
}

class Validation : IValidation {
    override fun validatePagination(page: Int, limit: Int) {
        val validationErrors = mutableListOf<Int>()
        if (page < 1) {
            validationErrors.add(INVALID_PAGE)
        }

        if (limit !in 5..PAGINATION_MAX_LIMIT) {
            validationErrors.add(INVALID_PAGE_LIMIT)
        }
        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }


    override fun isValidName(name: String?): Boolean {
        return name != null && name.matches(Regex("^[A-Za-z0-9\\s\\[\\]\\(\\)\\-.,&]{4,$NAME_MAX_LENGTH}$"))
    }

    override fun isValidPhone(phone: String?): Boolean {
        return phone != null && phone.matches(Regex("\\d{3}\\d{3}\\d{4}"))
    }

    override fun isValidId(id: String?): Boolean {
        return id != null && id.matches(Regex("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}"))
    }

    internal fun isValidIds(ids: List<String>?): Boolean {
        return !ids.isNullOrEmpty() && ids.all { isValidId(it) }
    }

    override fun isValidatePriceLevel(priceLevel: String): Boolean {
        return listOf("$", "$$", "$$$").contains(priceLevel)
    }

    override fun isValidRate(rate: Double): Boolean {
        return rate in 0.0..5.0
    }

    override fun isValidPrice(price: Double): Boolean {
        return price in 1.0..1000.0
    }

    override fun isValidDescription(description: String): Boolean {
        return description.length in DESCRIPTION_MIN_LENGTH..DESCRIPTION_MAX_LENGTH
    }


    override fun isValidLatitude(latitude: Double): Boolean {
        return (latitude != -1.0) && (latitude in LATITUDE_MIN..LATITUDE_MAX)
    }

    override fun isValidLongitude(longitude: Double): Boolean {
        return (longitude != -1.0) && (longitude in LONGITUDE_MIN..LONGITUDE_MAX)
    }

    override fun isValidLocation(latitude: Double, longitude: Double): Boolean {
        return isValidLatitude(latitude) && isValidLongitude(longitude)
    }

    override fun isValidAddress(address: String): Boolean {
        val regex = Regex("[0-9]+ [a-zA-Z]+ [a-zA-Z]+") // Example pattern: "123 Main Street"
        return regex.matches(address)
    }

    override fun isValidTime(time: String?): Boolean {
        return time != null && time.matches(Regex("\\d{2}:\\d{2}"))
    }

    override fun checkIsValidIds(id: String, listIds: List<String>) {
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
        const val NAME_MAX_LENGTH = 50
        const val LATITUDE_MIN = -90.0
        const val LATITUDE_MAX = 90.0
        const val LONGITUDE_MIN = -180.0
        const val LONGITUDE_MAX = 180.0
        const val NULL_DOUBLE = -1.0
        const val MAX_CUISINE = 3
        const val PAGINATION_MAX_LIMIT = 100
    }

}