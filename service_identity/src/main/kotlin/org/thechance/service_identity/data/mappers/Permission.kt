package org.thechance.service_identity.data.mappers

import org.bson.types.ObjectId
import org.thechance.service_identity.api.model.PermissionDto
import org.thechance.service_identity.data.collection.PermissionCollection
import org.thechance.service_identity.domain.entity.Permission

fun PermissionCollection.toEntity(): Permission {
    return Permission(
        id = id.toHexString(),
        permission = permission,
        isDeleted = isDeleted
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


fun Permission.toCollection(): PermissionCollection {
    return PermissionCollection(
        id = ObjectId(this.id),
        permission = this.permission
    )
}

