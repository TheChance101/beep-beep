package org.thechance.service_identity.data.mappers

import org.bson.types.ObjectId
import org.thechance.service_identity.api.model.PermissionDto
import org.thechance.service_identity.data.collection.PermissionCollection
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.utils.PermissionType

fun PermissionCollection.toEntity(): Permission {
    return Permission(
        id = id.toHexString(),
        permissionType = permissionType,
        isDeleted = isDeleted
    )
}

fun List<PermissionCollection>.toEntity(): List<Permission> = map { it.toEntity() }

fun PermissionDto.toEntity(): Permission {
    return Permission(
        id = id,
        permissionType = when (permissionType) {
            "CLIENT" -> PermissionType.CLIENT
            "ADMIN" -> PermissionType.ADMIN
            "DELIVERY" -> PermissionType.DELIVERY
            "TAXI_DRIVER" -> PermissionType.TAXI_DRIVER
            "RESTAURANT_OWNER" -> PermissionType.RESTAURANT_OWNER
            "SUPPORT" -> PermissionType.SUPPORT

            else -> {
                PermissionType.CLIENT
            }
        }
    )
}

fun Permission.toDto(): PermissionDto {
    return PermissionDto(
        id = id,
        permissionType = permissionType.toString()
    )
}


fun Permission.toCollection(): PermissionCollection {
    return PermissionCollection(
        id = ObjectId(this.id),
        permissionType = this.permissionType
    )
}

