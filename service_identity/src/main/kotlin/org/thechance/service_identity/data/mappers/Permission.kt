package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.PermissionCollection
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.endpoints.model.PermissionDto

fun PermissionCollection.toEntity(): Permission {
    return Permission(
        id = id,
        permission = permission,
    )
}

fun List<PermissionCollection>.toEntity(): List<Permission> = map { it.toEntity() }

fun Permission.toDto(): PermissionDto {
    return PermissionDto(
        id = id,
        permission = permission
    )
}

fun PermissionDto.toEntity(): Permission {
    return Permission(
        id = id ?: 0,
        permission = permission
    )
}

fun Permission.toCollection(id: Int): PermissionCollection {
    return PermissionCollection(
        id = id,
        permission = permission
    )
}

fun List<Permission>.toDto(): List<PermissionDto> = map { it.toDto() }
