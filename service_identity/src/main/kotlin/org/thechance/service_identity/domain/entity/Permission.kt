package org.thechance.service_identity.domain.entity

data class Permission(
    val id: Int,
    val permission: String,
)

data class CreatePermissionRequest(
    val permission: String,
)

data class UpdatePermissionRequest(
    val permission: String? = null
)