package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val fullName: String,
    val username: String,
    val password: String,
)
