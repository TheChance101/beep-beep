package org.thechance.service_identity.endpoints.model.mapper

import org.thechance.service_identity.domain.entity.Location
import org.thechance.service_identity.endpoints.model.LocationDto

fun Location.toDto() = LocationDto(
    latitude = latitude,
    longitude = longitude,
)