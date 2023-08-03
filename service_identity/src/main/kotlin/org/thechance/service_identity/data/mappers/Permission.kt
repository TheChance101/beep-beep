package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.PermissionCollection
import org.thechance.service_identity.domain.entity.Permission

fun PermissionCollection.toEntity(): Permission {
    return Permission(
        id = id.toHexString(),
        permissionType = permissionType
    )
}

fun List<PermissionCollection>.toEntity(): List<Permission> = map { it.toEntity() }
