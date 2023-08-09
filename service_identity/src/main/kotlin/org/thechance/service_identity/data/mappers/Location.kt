package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.LocationCollection
import org.thechance.service_identity.data.collection.UpdateLocationCollection
import org.thechance.service_identity.domain.entity.Location
import org.thechance.service_identity.endpoints.model.request.LocationDto
import org.thechance.service_identity.endpoints.model.request.UpdateLocationBody

fun LocationCollection.toEntity() = Location(
    latitude = latitude,
    longitude = longitude,
)


fun Location.toCollection() = LocationCollection(
    latitude = latitude,
    longitude = longitude,
)

fun Location.toUpdateRequest() = UpdateLocationCollection(
    latitude = latitude.takeIf { it != 0.0 },
    longitude = longitude.takeIf { it != 0.0 },
)


fun Location.toDto() = LocationDto(
    latitude = latitude,
    longitude = longitude,
)

fun LocationDto.toEntity() = Location(
    latitude = latitude,
    longitude = longitude,
)

fun UpdateLocationBody.toEntity() = Location(
    latitude = latitude ?: 0.0,
    longitude = longitude ?: 0.0,
)

