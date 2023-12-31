package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.Serializable
import org.thechance.service_identity.domain.util.Role

@Serializable
data class UserManagementDto(
    val id: String,
    val fullName: String,
    val username: String,
    val email: String,
    val phone: String,
    val country: String,
    val permission: Int = Role.END_USER
)
