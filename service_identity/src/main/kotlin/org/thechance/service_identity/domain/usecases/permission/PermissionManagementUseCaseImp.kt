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

    override suspend fun deletePermission(permissionId: String): Boolean {
        return dataBaseGateway.deletePermission(permissionId).takeIf { it }
    }

    override suspend fun getPermission(permissionId: String): Permission {
        return dataBaseGateway.getPermission(permissionId)
    }

    override suspend fun updatePermission(permissionId: String, permission: Permission): Boolean {
        return dataBaseGateway.updatePermission(permissionId, permission).takeIf { it }
    }

    override suspend fun getListOfPermission(permissionId: String): List<Permission> {
        return dataBaseGateway.getListOfPermission(permissionId)
    }
}