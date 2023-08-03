package org.thechance.service_identity.api.model

import kotlinx.serialization.Serializable
import org.thechance.service_identity.domain.entity.Address

@Serializable
data class AddressDto(
    val id: String = "",
    val userId: String,
    val latitude : Double,
    val longitude : Double,
)