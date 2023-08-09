package org.thechance.service_taxi.domain.util

import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.TaxiUpdateRequest
import org.thechance.service_taxi.domain.entity.Trip

@Single
class Validations {
    // region taxi
    fun validationTaxi(taxi: Taxi) {
        val validationErrors = mutableListOf<Int>()

        if (!isisValidPlateNumber(taxi.plateNumber)) {
            validationErrors.add(INVALID_PLATE)
        }
        if (taxi.color.isBlank()) {
            validationErrors.add(INVALID_COLOR)
        }
        if (taxi.type.isBlank()) {
            validationErrors.add(INVALID_CAR_TYPE)
        }
        if (!isValidId(taxi.driverId)) {
            validationErrors.add(INVALID_ID)
        }
        if (taxi.seats !in SEAT_RANGE) {
            validationErrors.add(SEAT_OUT_OF_RANGE)
        }
        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }

    fun validateUpdateRequest(taxi: TaxiUpdateRequest){
        val validationErrors = mutableListOf<Int>()

        taxi.plateNumber?.let {
            if (!isisValidPlateNumber(taxi.plateNumber)) {
                validationErrors.add(INVALID_PLATE)
            }
        }
        taxi.color?.let {
            if (taxi.color.isBlank()) {
                validationErrors.add(INVALID_COLOR)
            }
        }
        taxi.type?.let {
            if (taxi.type.isBlank()) {
                validationErrors.add(INVALID_CAR_TYPE)
            }
        }
        taxi.driverId?.let {
            if (!isValidId(taxi.driverId)) {
                validationErrors.add(INVALID_ID)
            }
        }
        taxi.seats?.let {
            if (taxi.seats !in SEAT_RANGE) {
                validationErrors.add(SEAT_OUT_OF_RANGE)
            }
        }
        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }
    // endregion

    // region trips
    fun validationTrip(trip: Trip) {
        val validationErrors = mutableListOf<Int>()

        if (!isValidId(trip.id)) {
            validationErrors.add(INVALID_ID)
        }
        if (!isValidId(trip.clientId)) {
            validationErrors.add(INVALID_ID)
        }
        trip.taxiId?.let {
            if (!isValidId(it)) {
                validationErrors.add(INVALID_ID)
            }
        }
        trip.driverId?.let {
            if (!isValidId(it)) {
                validationErrors.add(INVALID_ID)
            }
        }
        if (!isValidLocation(trip.startPoint.latitude, trip.startPoint.longitude)) {
            validationErrors.add(INVALID_LOCATION)
        }
        trip.destination?.let {
            if (!isValidLocation(it.latitude, it.longitude)) {
                validationErrors.add(INVALID_LOCATION)
            }
        }
        trip.rate?.let {
            if (!isValidRate(it)) {
                validationErrors.add(INVALID_RATE)
            }
        }
        if (!isValidPrice(trip.price)) {
            validationErrors.add(INVALID_PRICE)
        }
        if (trip.startDate != null && trip.endDate != null) {
            if (trip.startDate > trip.endDate) {
                validationErrors.add(INVALID_DATE)
            }
        }
        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }
    // endregion

    // region helpers
    fun isValidId(id: String): Boolean {
        return "^[0-9A-Fa-f]{24}$".toRegex().matches(id)
    }

    fun isValidPrice(price: Double): Boolean {
        return price >= 10.0
    }

    fun isValidLocation(latitude: Double, longitude: Double): Boolean {
        return isValidLatitude(latitude) && isValidLongitude(longitude)
    }

    private fun isValidLatitude(latitude: Double): Boolean {
        return latitude in LATITUDE_RANGE
    }

    private fun isValidLongitude(longitude: Double): Boolean {
        return longitude in LONGITUDE_RANGE
    }

    fun isisValidPlateNumber(plateNumber: String): Boolean {
        return PLATE_NUMBER_REGEX.any { it.matches(plateNumber) }
    }

    private fun isValidRate(rate: Double): Boolean {
        return rate in 0.0..5.0
    }
    // endregion

    private companion object {
        val LATITUDE_RANGE = -90.0..90.0
        val LONGITUDE_RANGE = -180.0..180.0
        val SEAT_RANGE = 2..8

        val PLATE_NUMBER_REGEX = listOf(
            "^\\d{5}\\s[A-Z]{3}\$".toRegex(), // Saudi Arabia
            "^[A-Z]{3}\\s\\d{5}\$".toRegex(), // United Arab Emirates
            "^\\d{4}\\s[A-Z]{3}\$".toRegex(), // Egypt
            "^[A-Z]{2}\\s\\d{4}\$".toRegex(), // Jordan
            "^\\d{6}\\s[A-Z]{2}\$".toRegex(), // Lebanon
            "^\\d{4}\\s[A-Z]{2}\$".toRegex(), // Morocco
            "^\\d{3}\\s[A-Z]{2}\$".toRegex(), // Tunisia
            "^[A-Z0-9]{1,2}-[0-9]{4,5}\$".toRegex(), // Iraq
        )
    }
}