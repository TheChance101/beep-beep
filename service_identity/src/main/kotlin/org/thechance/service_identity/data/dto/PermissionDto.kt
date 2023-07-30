package org.thechance.service_identity.data.dto

import kotlinx.serialization.Serializable
import org.litote.kmongo.Id

@Serializable
data class PermissionDto(
    val id: Id<PermissionDto>? = null,
    val permissionName: String
)