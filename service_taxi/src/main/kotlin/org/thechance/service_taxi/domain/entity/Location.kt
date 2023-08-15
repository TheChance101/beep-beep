package org.thechance.service_taxi.domain.entity

import org.thechance.service_taxi.domain.exceptions.INVALID_LOCATION
import org.thechance.service_taxi.domain.exceptions.MultiErrorException

data class Location(
    val latitude: Double,
    val longitude: Double,
){
    init {
        if (latitude < -90 || latitude > 90) {
            throw MultiErrorException(listOf(INVALID_LOCATION))
        }
        if (longitude < -180 || longitude > 180) {
            throw MultiErrorException(listOf(INVALID_LOCATION))
        }
    }
}