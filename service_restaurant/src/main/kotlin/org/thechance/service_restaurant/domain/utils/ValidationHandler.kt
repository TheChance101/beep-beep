package org.thechance.service_restaurant.domain.utils

//class ValidationHandler {
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

    fun isValidatePhone(phone: String?): Boolean {
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

    fun validatePriceLevel(priceLevel: String?): Boolean {
        return priceLevel.isNullOrBlank() || listOf("$", "$$", "$$$", "$$$$").contains(priceLevel)
    }

    fun validateRate(rate: Double): Boolean {
        return rate in 0.0..5.0
    }

    fun validatePrice(price: Double): Boolean {
        return  price in 1.0..1000.0
    }


    fun validateDescription(description: String): Boolean {
        return description.length <= DESCRIPTION_MAX_LENGTH
    }

//}