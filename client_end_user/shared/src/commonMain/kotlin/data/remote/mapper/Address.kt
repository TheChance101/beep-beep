package data.remote.mapper

import data.remote.model.AddressDto
import domain.entity.Address

fun AddressDto.toEntity(): Address {
    return Address(
        id = id ?: "",
        location = location?.toEntity(),
        address = address ?: "",
    )
}
