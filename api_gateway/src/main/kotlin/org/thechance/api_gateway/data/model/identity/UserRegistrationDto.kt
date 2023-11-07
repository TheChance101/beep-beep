package org.thechance.api_gateway.data.model.identity

import kotlinx.serialization.Serializable

@Serializable
data class UserRegistrationDto(
    val fullName: String,
    val username: String,
    val phone: String,
    val password: String,
    val email: String,
    val address: String,
)