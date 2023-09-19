package org.thechance.service_restaurant.domain.utils

import org.thechance.service_restaurant.domain.entity.Location
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PAGE
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PAGE_LIMIT
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException

interface IValidation {
    fun validatePagination(page: Int, limit: Int)
    fun isValidName(name: String?): Boolean
    fun isValidPhone(phone: String?, currency: String): Boolean
    fun isValidId(id: String?): Boolean
    fun isValidatePriceLevel(priceLevel: String): Boolean
    fun isValidRate(rate: Double): Boolean
    fun isValidPrice(price: Double): Boolean
    fun isValidDescription(description: String): Boolean
    fun isValidLatitude(latitude: Double): Boolean
    fun isValidLongitude(longitude: Double): Boolean
    fun isValidLocation(location: Location): Boolean
    fun isValidTime(time: String?): Boolean
    fun checkIsValidIds(id: String, listIds: List<String>)

    fun isValidAddress(address: String): Boolean
    fun isValidEmail(ownerEmail: String): Boolean

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

    override fun isValidPhone(phone: String?, currency: String): Boolean {
        val phoneRegex = getPhoneRegex(currency)
        return phone != null && phone.matches(Regex(phoneRegex))
    }

    private fun getPhoneRegex(currency: String): String {
        return when (currency) {
            "EGP" -> "^01\\d{9}\$"
            "IQD" -> "^07\\d{9}\$"
            "SYP" -> "^09\\d{9}\$"
            "ILS" -> "^(05|09)\\d{9}\$"
            else -> "^\\d{10}\$"
        }
    }

    override fun isValidId(id: String?): Boolean {
        val objectIdPattern = "[0-9a-fA-F]{24}"
        return id != null && id.matches(Regex(objectIdPattern))
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

    override fun isValidLocation(location: Location): Boolean {
        return isValidLatitude(location.latitude) && isValidLongitude(location.longitude)
    }

    override fun isValidAddress(address: String): Boolean {
        val regex = Regex("[0-9]+ [a-zA-Z]+ [a-zA-Z]+") // Example pattern: "123 Main Street"
        return regex.matches(address)
    }

    override fun isValidEmail(ownerEmail: String): Boolean {
        return "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$".toRegex().matches(ownerEmail)
    }

    override fun isValidTime(time: String?): Boolean {
        val pattern = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$"
        return if (time != null) Regex(pattern).matches(time) else false

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