package data.remote.mapper

import data.remote.model.AddressDto
import domain.entity.Address

fun AddressDto.toEntity(): Address {
    return Address(
        id = id ?: "",
        address = address ?: "",
        location = location?.toEntity()
    )
}
