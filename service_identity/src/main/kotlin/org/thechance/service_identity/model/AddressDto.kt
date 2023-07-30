package org.thechance.service_identity.model

import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.thechance.service_identity.entity.Address

@Serializable
data class AddressDto(
    val id: Id<AddressDto>? = null,
    val userId: Id<UserDto>,
    val country: String,
    val city: String,
    val street: String? = null,
    val code: String? = null,
    val houseNumber: Int? = null
)

fun AddressDto.toAddress(): Address {
    return Address(
        id = id.toString(),
        userId = userId.toString(),
        country = country,
        city = city,
        street = street,
        code = code,
        houseNumber = houseNumber
    )
}
