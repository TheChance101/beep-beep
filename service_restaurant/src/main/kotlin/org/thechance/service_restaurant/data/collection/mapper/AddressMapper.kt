package org.thechance.service_restaurant.data.collection.mapper

import org.thechance.service_restaurant.data.collection.LocationCollection
import org.thechance.service_restaurant.domain.entity.Location

fun LocationCollection.toEntity(): Location {
    return Location(
        latitude = latitude,
        longitude = longitude,
    )
}

fun Location.toCollection() = LocationCollection(
    latitude = latitude,
    longitude = longitude,
)
