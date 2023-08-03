package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.PermissionCollection
import org.thechance.service_identity.domain.entity.Permission

fun PermissionCollection.toPermission(): Permission {
    return Permission(
        id = id.toHexString(),
        permissionType = permissionType
    )
}

fun List<PermissionCollection>.toUserEntity(): List<Permission> = map { it.toPermission() }
