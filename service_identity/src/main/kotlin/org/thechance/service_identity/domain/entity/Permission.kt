package org.thechance.service_identity.domain.entity

import org.thechance.service_identity.utils.PermissionType

data class Permission(
    val id:String,
    val permissionType: PermissionType
)
