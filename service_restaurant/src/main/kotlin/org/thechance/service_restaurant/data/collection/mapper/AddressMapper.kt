package org.thechance.service_restaurant.data.collection.mapper

import org.thechance.service_restaurant.data.collection.AddressCollection
import org.thechance.service_restaurant.domain.entity.Address

fun AddressCollection.toEntity(): Address {
    return Address(
        latitude = latitude,
        longitude = longitude,
    )
}

fun Address.toCollection() = AddressCollection(
    latitude = latitude,
    longitude = longitude,
)
