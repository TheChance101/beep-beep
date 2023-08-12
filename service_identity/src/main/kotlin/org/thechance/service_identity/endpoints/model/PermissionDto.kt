package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class PermissionDto(
    val id: Int,
    val permission: String
)

@Serializable
data class CreatePermissionDocument(
    val permission: String
)

@Serializable
data class UpdatePermissionDocument(
    val permission: String? = null
)