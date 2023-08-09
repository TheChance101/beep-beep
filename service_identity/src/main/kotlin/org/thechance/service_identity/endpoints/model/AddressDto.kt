package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.Serializable
import org.thechance.service_identity.endpoints.model.request.LocationDto
import org.thechance.service_identity.endpoints.model.request.UpdateLocationBody

@Serializable
data class AddressDto(
    val id: String,
    val location: LocationDto,
)

@Serializable
data class CreateAddressRequest(
    val location: LocationDto,
)

@Serializable
data class UpdateAddressRequest(
    val location: UpdateLocationBody? = null,
)