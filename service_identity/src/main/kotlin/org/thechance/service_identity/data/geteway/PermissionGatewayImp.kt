package org.thechance.service_identity.data.geteway

import com.mongodb.client.model.Filters
import org.bson.types.ObjectId
import org.thechance.service_identity.data.DataBaseContainer
import org.thechance.service_identity.data.collection.PermissionCollection
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.gateway.PermissionGateway
import org.thechance.service_identity.utils.Constants

class PermissionGatewayImp(private val container: DataBaseContainer) : PermissionGateway {
    private val permissionCollection by lazy {
        container.database.getCollection<PermissionCollection>(
            Constants.PERMISSION_COLLECTION_NAME
        )
    }

    override suspend fun addPermission(permission: Permission): Boolean {
        return permissionCollection.insertOne(permission.toPermissionCollection()).wasAcknowledged()

    }

    override suspend fun deletePermission(permissionId: String): Boolean {
        return try {
            val objectId = ObjectId(permissionId)
            permissionCollection.deleteOne(Filters.eq("_id", objectId))
            true
        } catch (e: Exception) {
            false
        }

    }

    override suspend fun updatePermission(id: String, permission: Permission): Boolean {
        return permissionCollection.updateOneById(
            id = ObjectId(id),
            update = permission.toPermissionCollection(),
        ).wasAcknowledged()
    }
}