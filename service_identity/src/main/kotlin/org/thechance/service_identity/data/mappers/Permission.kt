package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.PermissionCollection
import org.thechance.service_identity.data.collection.UpdatePermissionCollection
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.endpoints.model.CreatePermissionRequest
import org.thechance.service_identity.endpoints.model.PermissionDto
import org.thechance.service_identity.endpoints.model.UpdatePermissionRequest

fun PermissionCollection.toEntity(): Permission {
    return Permission(
        id = _id,
        permission = permission,
    )
}

fun List<PermissionCollection>.toEntity(): List<Permission> = map { it.toEntity() }

fun PermissionDto.toEntity(): Permission {
    return Permission(
        id = id,
        permission = permission
    )
}

fun Permission.toDto(): PermissionDto {
    return PermissionDto(
        id = id,
        permission = permission
    )
}

fun List<Permission>.toDto(): List<PermissionDto> = map { it.toDto() }

fun Permission.toCollection(incrementedId: Int): PermissionCollection {
    return PermissionCollection(
        incrementedId,
        permission = this.permission
    )
}

fun Permission.toCollection(): PermissionCollection {
    return PermissionCollection(
        _id = id,
        permission = this.permission,
    )
}

fun List<Permission>.toCollection(): List<PermissionCollection> = map { it.toCollection() }

fun Permission.toUpdateRequest() = UpdatePermissionCollection(
    permission = permission.ifBlank { null }
)

fun CreatePermissionRequest.toEntity(): Permission {
    return Permission(
        id = 0,
        permission = permission
    )
}

fun UpdatePermissionRequest.toEntity() = Permission(
    id = 0,
    permission = permission ?: ""
)