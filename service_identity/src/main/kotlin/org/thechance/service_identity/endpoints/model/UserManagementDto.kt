package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.Serializable
import org.thechance.service_identity.domain.util.Role

@Serializable
data class UserManagementDto(
    val id: String,
    val fullName: String,
    val username: String,
    val email: String,
    val permission : Int = Role.END_USER
)

@Serializable
data class UsersManagementDto(
    val items: List<UserManagementDto>,
    val total: Long
)
