package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.api.model.PermissionDto
import org.thechance.service_identity.data.collection.PermissionCollection
import org.thechance.service_identity.domain.entity.Permission

fun PermissionCollection.toEntity(): Permission {
    return Permission(
        id = id.toHexString(),
        permissionType = permissionType
    )
}

fun List<PermissionCollection>.toEntity(): List<Permission> = map { it.toEntity() }

fun Permission.toDto(): PermissionDto {
    return PermissionDto(
        id = id,
        permissionType = permissionType
    )
}

fun PermissionDto.toEntity(): Permission {
    return Permission(
        id = id,
        permissionType = permissionType
    )
}
