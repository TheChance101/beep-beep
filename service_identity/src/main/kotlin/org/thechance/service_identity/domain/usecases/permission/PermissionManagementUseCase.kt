package org.thechance.service_identity.domain.usecases.permission

import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.entity.Permission

interface PermissionManagementUseCase {
    suspend fun createPermission(permission: Permission): Boolean
    suspend fun deletePermission(permissionId: String): Boolean
    suspend fun getPermission(permissionId: String): Permission
    suspend fun updatePermission(permissionId: String, permission: Permission): Boolean
    suspend fun getListOfPermission(permissionId: String): List<Permission>


}