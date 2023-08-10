package org.thechance.service_identity.data.mappers

import org.bson.types.ObjectId
import org.thechance.service_identity.data.collection.AddressCollection
import org.thechance.service_identity.data.collection.CreateAddressDocument
import org.thechance.service_identity.data.collection.UpdateAddressDocument
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.entity.CreateAddressRequest
import org.thechance.service_identity.domain.entity.Location
import org.thechance.service_identity.domain.entity.UpdateAddressRequest
import org.thechance.service_identity.endpoints.model.AddressDto

fun AddressCollection.toEntity(): Address {
    return Address(
        id = id.toHexString(),
        location = location.toEntity(),
    )
}


fun UpdateAddressDocument.toUpdateRequest(): UpdateAddressRequest {
    return UpdateAddressRequest(
        location = location
    )
}

fun Address.toDto(): AddressDto {
    return AddressDto(
        id = id,
        location = location.toDto()
    )
}

fun List<Address>.toDto(): List<AddressDto> {
    return map { it.toDto() }
}


fun List<AddressCollection>.toEntity(): List<Address> {
    return this.map { it.toEntity() }
}

fun CreateAddressRequest.toCollection(userId: String): AddressCollection =
    AddressCollection(
        userId = ObjectId(userId),
        location = location.toCollection()
    )

fun UpdateAddressRequest.toUpdateDocument(): UpdateAddressDocument =
    UpdateAddressDocument(
        location = location
    )

fun CreateAddressDocument.toCreateRequest(): CreateAddressRequest =
    CreateAddressRequest(
        location = Location(
            latitude = location.latitude,
            longitude = location.longitude
        )
    )