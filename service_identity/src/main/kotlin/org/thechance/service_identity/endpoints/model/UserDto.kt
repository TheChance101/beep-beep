package org.thechance.service_identity.api.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String? = null,
    val fullName: String? = null,
    val username: String? = null,
    val password: String? = null,
)
