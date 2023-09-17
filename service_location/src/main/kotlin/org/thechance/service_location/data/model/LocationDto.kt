package org.thechance.service_location.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.thechance.service_location.util.INVALID_LOCATION
import org.thechance.service_location.util.InvalidLocationException

@Serializable
data class LocationDto(
    @SerialName("latitude") val latitude: Double = LATITUDE_MIN,
    @SerialName("longitude") val longitude: Double = LONGITUDE_MIN
) {
    init {
        if (latitude < LATITUDE_MIN || latitude > LATITUDE_MAX) {
            throw InvalidLocationException(INVALID_LOCATION)
        }
        if (longitude < LONGITUDE_MIN || longitude > LONGITUDE_MAX) {
            throw InvalidLocationException(INVALID_LOCATION)
        }
    }

    companion object {
        const val LATITUDE_MIN = -90.0
        const val LATITUDE_MAX = 90.0
        const val LONGITUDE_MIN = -180.0
        const val LONGITUDE_MAX = 180.0
    }
}
