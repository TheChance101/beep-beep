package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.LocationCollection
import org.thechance.service_identity.domain.entity.Location
import org.thechance.service_identity.endpoints.model.request.LocationDto

fun LocationCollection.toEntity() = Location(
    latitude = latitude,
    longitude = longitude,
)


fun Location.toCollection() = LocationCollection(
    latitude = latitude,
    longitude = longitude,
)

fun Location.toDto() = LocationDto(
    latitude = latitude,
    longitude = longitude,
)

fun LocationDto.toEntity() = Location(
    latitude = latitude,
    longitude = longitude,
)

