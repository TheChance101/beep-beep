package org.thechance.service_identity.endpoints.model.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val fullName: String? = null,
    val username: String? = null,
    val password: String? = null,
    val email: String? = null,
)