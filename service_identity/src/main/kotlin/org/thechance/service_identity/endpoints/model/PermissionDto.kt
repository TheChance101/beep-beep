package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class PermissionDto(
    val id: Int,
    val permission: Int
)
