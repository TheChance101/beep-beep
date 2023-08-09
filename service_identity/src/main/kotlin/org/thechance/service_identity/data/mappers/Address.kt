package org.thechance.service_identity.data.mappers

import org.bson.types.ObjectId
import org.thechance.service_identity.data.collection.AddressCollection
import org.thechance.service_identity.data.collection.UpdateAddressCollection
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.entity.Location
import org.thechance.service_identity.endpoints.model.AddressDto
import org.thechance.service_identity.endpoints.model.CreateAddressRequest
import org.thechance.service_identity.endpoints.model.UpdateAddressRequest

fun AddressCollection.toEntity(): Address {
    return Address(
        id = id.toHexString(),
        location = location.toEntity(),
    )
}

fun AddressDto.toEntity(): Address {
    return Address(
        id = id,
        location = location.toEntity()
    )
}

fun UpdateAddressRequest.toEntity(): Address {
    return Address(
        id = "",
        location = location?.toEntity() ?: Location(0.0, 0.0)
    )
}

fun Address.toDto(): AddressDto {
    return AddressDto(
        id = id,
        location = location.toDto()
    )
}

fun CreateAddressRequest.toEntity(): Address {
    return Address(
        id = "",
        location = location.toEntity()
    )
}

fun List<Address>.toDto(): List<AddressDto> {
    return map { it.toDto() }
}

fun Address.toCollection(userId: String): AddressCollection {
    return AddressCollection(
        userId = ObjectId(userId),
        location = this.location.toCollection()
    )
}

fun Address.toUpdateRequest(): UpdateAddressCollection {
    return UpdateAddressCollection(
        location = this.location.toUpdateRequest()
    )
}

fun List<AddressCollection>.toEntity(): List<Address> {
    return this.map { it.toEntity() }
}