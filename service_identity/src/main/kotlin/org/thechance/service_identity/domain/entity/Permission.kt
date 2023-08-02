package org.thechance.service_identity.domain.entity

import org.bson.types.ObjectId
import org.litote.kmongo.id.toId
import org.thechance.service_identity.api.model.PermissionDto
import org.thechance.service_identity.data.collection.AddressCollection
import org.thechance.service_identity.data.collection.PermissionCollection
import org.thechance.service_identity.utils.PermissionType

data class Permission(
    val id: String,
    val permissionType: PermissionType
) {
    fun toPermissionDto(): PermissionDto {
        return PermissionDto(
            id = id,
            permissionType = permissionType
        )
    }

    fun toPermissionCollection(): PermissionCollection {
        return PermissionCollection(
            id = ObjectId(this.id),
            permissionType = this.permissionType
        )
    }
}
