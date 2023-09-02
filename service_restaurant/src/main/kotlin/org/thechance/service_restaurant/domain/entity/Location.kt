package org.thechance.service_restaurant.domain.entity

import org.thechance.service_restaurant.domain.utils.Validation.Companion.LATITUDE_MAX
import org.thechance.service_restaurant.domain.utils.Validation.Companion.LATITUDE_MIN
import org.thechance.service_restaurant.domain.utils.Validation.Companion.LONGITUDE_MAX
import org.thechance.service_restaurant.domain.utils.Validation.Companion.LONGITUDE_MIN
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_LOCATION
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException

data class Location(
    val latitude: Double,
    val longitude: Double,
){

    init {
        if (latitude < LATITUDE_MIN || latitude > LATITUDE_MAX) {
            throw MultiErrorException(listOf(INVALID_LOCATION))
        }
        if (longitude < LONGITUDE_MIN || longitude > LONGITUDE_MAX) {
            throw MultiErrorException(listOf(INVALID_LOCATION))
        }
    }
}
