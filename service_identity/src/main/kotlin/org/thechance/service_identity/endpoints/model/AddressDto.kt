package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.Serializable
import org.thechance.service_identity.endpoints.model.request.LocationDto

@Serializable
data class AddressDto(
    val id: String,
    val userId: String,
    val location: LocationDto,
)