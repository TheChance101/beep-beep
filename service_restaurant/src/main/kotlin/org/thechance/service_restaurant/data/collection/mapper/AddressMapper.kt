package org.thechance.service_restaurant.data.collection.mapper

import org.thechance.service_restaurant.data.collection.AddressCollection
import org.thechance.service_restaurant.domain.entity.Location

fun AddressCollection.toEntity(): Location {
    return Location(
        latitude = latitude,
        longitude = longitude,
    )
}

fun Location.toCollection() = AddressCollection(
    latitude = latitude,
    longitude = longitude,
)
