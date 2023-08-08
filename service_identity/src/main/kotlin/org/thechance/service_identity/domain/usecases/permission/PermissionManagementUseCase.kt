package org.thechance.service_identity.domain.usecases.permission

import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.entity.Permission

interface PermissionManagementUseCase {
    suspend fun createPermission(permission: Permission): Boolean
    suspend fun deletePermission(permissionId: Int): Boolean
    suspend fun getPermission(permissionId: Int): Permission
    suspend fun updatePermission(permissionId: Int, permission: Permission): Boolean
    suspend fun getListOfPermission(permissionId: Int): List<Permission>

}