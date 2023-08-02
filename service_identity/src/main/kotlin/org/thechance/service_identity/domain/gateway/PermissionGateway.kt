package org.thechance.service_identity.domain.gateway

import org.thechance.service_identity.domain.entity.Permission

interface PermissionGateway {
    suspend fun getPermission(id: String): Permission?
    suspend fun addPermission(permission: Permission): Boolean
    suspend fun updatePermission(id: String, permission: Permission): Boolean
    suspend fun deletePermission(permissionId: String): Boolean
}