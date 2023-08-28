package org.thechance.api_gateway.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class UserManagement(
    val id : String,
    val fullName : String,
    val username : String,
    val email : String
)