package org.thechance.service_identity.domain.entity

import org.thechance.service_identity.domain.util.INVALID_ADDRESS_LOCATION

data class Address(
    val id: String,
    val location: Location,
) {
    init {
        require(location.latitude in -90.0..90.0) { throw Throwable(INVALID_ADDRESS_LOCATION) }
        require(location.longitude in -180.0..180.0) { throw Throwable(INVALID_ADDRESS_LOCATION) }
    }
}