package org.thechance.service_identity.data.collection

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.thechance.service_identity.data.geteway.DataBaseGatewayImp.Companion.CLIENT_PERMISSION

@Serializable
data class PermissionCollection(
    @SerialName("_id")
    val id: Int = 1,
    @SerialName("permission")
    val permission: Int = CLIENT_PERMISSION,
    @SerialName("is_deleted")
    val isDeleted: Boolean = false
)

@Serializable
data class UserPermissionsCollection(
    @SerialName("permissions")
    val userPermissions: List<PermissionCollection> = emptyList()
)