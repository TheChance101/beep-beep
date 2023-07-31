package org.thechance.service_identity.api.model

import kotlinx.serialization.Serializable
import org.thechance.service_identity.utils.PermissionType

@Serializable
data class PermissionDto(
    val id: String,
    val permissionType: PermissionType
)
