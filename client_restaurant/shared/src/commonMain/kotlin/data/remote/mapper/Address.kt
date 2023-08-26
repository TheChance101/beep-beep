package data.remote.mapper

import data.remote.model.AddressDto
import domain.entity.Address


fun AddressDto.toEntity(): Address {
    return Address(
        latitude = latitude ?: 0.0,
        longitude = longitude ?: 0.0
    )
}