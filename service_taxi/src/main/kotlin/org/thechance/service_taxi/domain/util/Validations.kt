package org.thechance.service_taxi.domain.util

import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.Trip

fun validationTaxi(taxi: Taxi) {
    val validationErrors = mutableListOf<Int>()

    if (!isValidatePlateNumber(taxi.plateNumber)) {
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
    if (!validateLocation(trip.startPoint.latitude, trip.startPoint.longitude)) {
        validationErrors.add(INVALID_LOCATION)
    }
    trip.destination?.let {
        if (!validateLocation(it.latitude, it.longitude)) {
            validationErrors.add(INVALID_LOCATION)
        }
    }
    trip.rate?.let {
        if (!validateRate(it)){
            validationErrors.add(INVALID_RATE)
        }
    }
    if (!validatePrice(trip.price)){
        validationErrors.add(INVALID_PRICE)
    }
    if (trip.startDate != null && trip.endDate != null){
        if (trip.startDate > trip.endDate){
            validationErrors.add(INVALID_DATE)
        }
    }
    if (validationErrors.isNotEmpty()) {
        throw MultiErrorException(validationErrors)
    }
}


fun isValidId(id: String): Boolean {
    return "^[0-9A-Fa-f]{24}$".toRegex().matches(id)
}

fun validatePrice(price: Double): Boolean {
    return price >= 10.0
}

fun validateLatitude(latitude: Double): Boolean {
    return latitude in LATITUDE_RANGE
}

fun validateLongitude(longitude: Double): Boolean {
    return longitude in LONGITUDE_RANGE
}

fun validateLocation(latitude: Double, longitude: Double): Boolean {
    return validateLatitude(latitude) && validateLongitude(longitude)
}

fun isValidatePlateNumber(plateNumber: String): Boolean {
    return PLATE_NUMBER_REGEX.any { it.matches(plateNumber) }
}

fun validateRate(rate: Double): Boolean {
    return rate in 0.0..5.0
}


val LATITUDE_RANGE = -90.0..90.0
val LONGITUDE_RANGE = -180.0..180.0
val SEAT_RANGE = 4..5

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
