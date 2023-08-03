package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.thechance.service_identity.utils.PermissionType

@Serializable
data class PermissionCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    @SerialName("permission_type")
    val permissionType: PermissionType = PermissionType.CLIENT,
    @SerialName("is_deleted")
    val isDeleted: Boolean = false
)

@Serializable
data class UserPermissionsCollection(
    @SerialName("permissions")
    val userPermissions: List<PermissionCollection> = emptyList()
)