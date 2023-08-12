package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.PermissionCollection
import org.thechance.service_identity.domain.entity.CreatePermissionRequest
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.entity.UpdatePermissionRequest
import org.thechance.service_identity.endpoints.model.CreatePermissionDocument
import org.thechance.service_identity.endpoints.model.PermissionDto
import org.thechance.service_identity.endpoints.model.UpdatePermissionDocument

fun PermissionCollection.toEntity(): Permission {
    return Permission(
        id = _id,
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

fun List<Permission>.toDto(): List<PermissionDto> = map { it.toDto() }

fun CreatePermissionDocument.toEntity(): CreatePermissionRequest {
    return CreatePermissionRequest(
        permission = permission
    )
}

fun CreatePermissionRequest.toCollection(id: Int): PermissionCollection {
    return PermissionCollection(
        _id = id,
        permission = permission
    )
}

fun UpdatePermissionDocument.toEntity() = UpdatePermissionRequest(
    permission = permission
)

fun UpdatePermissionRequest.toUpdateDocument() = UpdatePermissionDocument(
    permission = permission
)