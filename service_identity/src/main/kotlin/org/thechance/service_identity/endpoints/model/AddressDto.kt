package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    val id: String,
    val location: LocationDto,
)