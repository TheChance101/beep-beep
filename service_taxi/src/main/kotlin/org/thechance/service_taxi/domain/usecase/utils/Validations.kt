package org.thechance.service_taxi.domain.usecase.utils

interface IValidations {
    fun isValidId(id: String): Boolean
    fun isValidPlateNumber(plateNumber: String): Boolean
    fun isValidLocation(latitude: Double, longitude: Double): Boolean
    fun isValidRate(rate: Double): Boolean
    fun isValidPrice(price: Double): Boolean
    fun isValidCarType(type: String): Boolean
}

class Validations : IValidations {
    override fun isValidId(id: String): Boolean {
        val objectIdPattern = "[0-9a-fA-F]{24}"
        return id.matches(Regex(objectIdPattern))
    }

    override fun isValidPrice(price: Double): Boolean {
        return price >= 10.0
    }

    override fun isValidCarType(type: String): Boolean {
        return "^[A-Za-z0-9\\s\\-]+\$".toRegex().matches(type)
    }

    override fun isValidLocation(latitude: Double, longitude: Double): Boolean {
        return isValidLatitude(latitude) && isValidLongitude(longitude)
    }

    override fun isValidPlateNumber(plateNumber: String): Boolean {
        return PLATE_NUMBER_REGEX.any { it.matches(plateNumber) }
    }

    override fun isValidRate(rate: Double): Boolean {
        return rate in 0.0..5.0
    }

    private fun isValidLatitude(latitude: Double): Boolean {
        return latitude in LATITUDE_RANGE
    }

    private fun isValidLongitude(longitude: Double): Boolean {
        return longitude in LONGITUDE_RANGE
    }

    companion object {
        val LATITUDE_RANGE = -90.0..90.0
        val LONGITUDE_RANGE = -180.0..180.0

        val PLATE_NUMBER_REGEX = listOf(
            "^\\d{5}\\s[A-Z]{3}\$".toRegex(), // Saudi Arabia
            "^[A-Z]{3}\\s\\d{5}\$".toRegex(), // United Arab Emirates
            "^\\d{4}\\s[A-Z]{3}\$".toRegex(), // Egypt
            "^[A-Z]{2}\\s\\d{4}\$".toRegex(), // Jordan
            "^\\d{6}\\s[A-Z]{2}\$".toRegex(), // Lebanon
            "^\\d{4}\\s[A-Z]{2}\$".toRegex(), // Morocco
            "^\\d{3}\\s[A-Z]{2}\$".toRegex(), // Tunisia
            "^[A-Z0-9]{1,2}-[0-9]{4,5}\$".toRegex(), // Iraq
            "^[A-Z0-9]{1,2}-[0-9]{4,5}\$".toRegex(), // Syria
            "^[A-Z0-9]{1,7}\$".toRegex(), // United States
        )
    }
}