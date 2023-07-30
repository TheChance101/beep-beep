package org.thechance.service_identity.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.thechance.service_identity.entity.Permission

@Serializable
data class PermissionDto(
    @BsonId
    val id: Id<PermissionDto>? = null,
    val permissionType: PermissionType = PermissionType.CLIENT
)

enum class PermissionType {
    CLIENT,
    ADMIN,
    DELIVERY,
    TAXI_DRIVER,
    RESTAURANT_OWNER,
    SUPPORT
}

fun PermissionDto.toPermission(): Permission {
    return Permission(
        id = id.toString(),
        permissionType = permissionType
    )
}