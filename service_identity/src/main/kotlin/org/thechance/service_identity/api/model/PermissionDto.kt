package org.thechance.service_identity.api.model

import kotlinx.serialization.Serializable
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.utils.PermissionType

@Serializable
data class PermissionDto(
    val id: String,
    val permissionType: String
) {
    fun toPermission(): Permission {
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
}
