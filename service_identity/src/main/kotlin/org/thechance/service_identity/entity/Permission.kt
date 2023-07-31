package org.thechance.service_identity.entity

import org.thechance.service_identity.api.model.PermissionDto
import org.thechance.service_identity.utils.PermissionType

data class Permission(
    val id:String,
    val permissionType: PermissionType
){
    fun toPermissionDto(): PermissionDto {
        return PermissionDto(
            id = id,
            permissionType = permissionType
        )
    }
}
