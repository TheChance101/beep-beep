package org.thechance.service_identity.domain.gateway

import org.thechance.service_identity.domain.entity.Permission

interface DataBaseGateway {
    suspend fun getPermission(permissionId: String): Permission?
    suspend fun addPermission(permission: Permission): Boolean
    suspend fun updatePermission(permissionId: String, permission: Permission): Boolean
    suspend fun deletePermission(permissionId: String): Boolean
}