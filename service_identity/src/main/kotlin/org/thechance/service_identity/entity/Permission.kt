package org.thechance.service_identity.entity

import org.thechance.service_identity.model.PermissionType

data class Permission(
    val id:String,
    val permissionType: PermissionType = PermissionType.CLIENT
)
