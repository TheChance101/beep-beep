package org.thechance.service_identity.domain.usecases.permission

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.gateway.DataBaseGateway

@Single
class PermissionManagementUseCaseImp(
    private val dataBaseGateway: DataBaseGateway
) : PermissionManagementUseCase {
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