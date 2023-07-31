package org.thechance.service_identity.data.collection

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.thechance.service_identity.entity.Permission
import org.thechance.service_identity.utils.PermissionType

data class PermissionCollection(
    @BsonId
    val id: ObjectId = ObjectId(),
    val permissionType: PermissionType = PermissionType.CLIENT
){
    fun toPermission(): Permission {
        return Permission(
            id = id.toHexString(),
            permissionType = permissionType
        )
    }
}
