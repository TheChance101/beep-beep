package org.thechance.service_identity.domain.usecases.permission

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.gateway.DataBaseGateway

interface IPermissionManagementUseCase {

    suspend fun createPermission(permission: Permission): Boolean

    suspend fun deletePermission(permissionId: Int): Boolean

    suspend fun getPermission(permissionId: Int): Permission

    suspend fun updatePermission(permissionId: Int, permission: Permission): Boolean

    suspend fun getListOfPermission(): List<Permission>

}

@Single
class PermissionManagementUseCase(
    private val dataBaseGateway: DataBaseGateway
) : IPermissionManagementUseCase {
    override suspend fun createPermission(permission: Permission): Boolean {
        return dataBaseGateway.addPermission(permission)
    }

    override suspend fun deletePermission(permissionId: Int): Boolean {
        return dataBaseGateway.deletePermission(permissionId)
    }

    override suspend fun getPermission(permissionId: Int): Permission {
        return dataBaseGateway.getPermission(permissionId)
    }

    override suspend fun updatePermission(permissionId: Int, permission: Permission): Boolean {
        return dataBaseGateway.updatePermission(permissionId, permission)
    }

    override suspend fun getListOfPermission(): List<Permission> {
        return dataBaseGateway.getListOfPermission()
    }
}