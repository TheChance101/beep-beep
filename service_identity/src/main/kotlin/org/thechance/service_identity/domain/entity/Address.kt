package org.thechance.service_identity.domain.entity

import org.thechance.service_identity.endpoints.model.UpdateLocationBody

data class Address(
    val id: String,
    val location: Location,
)

data class CreateAddressRequest(
    val location: Location
)

data class UpdateAddressRequest(
    val location: UpdateLocationBody? = null
)