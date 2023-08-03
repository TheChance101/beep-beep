package org.thechance.service_identity.data.geteway

import com.mongodb.client.model.Filters
import org.bson.types.ObjectId
import org.litote.kmongo.eq
import org.litote.kmongo.setValue
import org.thechance.service_identity.data.DataBaseContainer
import org.thechance.service_identity.data.collection.PermissionCollection
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.gateway.PermissionGateway
import org.thechance.service_identity.utils.Constants
import org.thechance.service_identity.utils.isDocumentModified

class PermissionGatewayImp(private val container: DataBaseContainer) : PermissionGateway {
    private val permissionCollection by lazy {
        container.database.getCollection<PermissionCollection>(
            Constants.PERMISSION_COLLECTION_NAME
        )
    }

    override suspend fun getPermission(id: String): Permission? {
        return permissionCollection.findOneById(ObjectId(id))?.toPermission()
            ?: throw Exception("Wallet not found")
    }

    override suspend fun addPermission(permission: Permission): Boolean {
        return permissionCollection.insertOne(permission.toPermissionCollection()).wasAcknowledged()

    }

    override suspend fun deletePermission(permissionId: String): Boolean {
        return permissionCollection.updateOne(
            filter = Filters.and(
                PermissionCollection::id eq ObjectId(permissionId),
                PermissionCollection::isDeleted eq false
            ),
            update = setValue(PermissionCollection::isDeleted, true)
        ).isDocumentModified()
    }

    override suspend fun updatePermission(id: String, permission: Permission): Boolean {
        return permissionCollection.updateOneById(
            id = ObjectId(id),
            update = permission.toPermissionCollection(),
        ).wasAcknowledged()
    }
}