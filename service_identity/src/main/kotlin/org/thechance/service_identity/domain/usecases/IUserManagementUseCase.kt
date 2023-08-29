package org.thechance.service_identity.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.UserManagement
import org.thechance.service_identity.domain.gateway.IDataBaseGateway

interface IUserManagementUseCase {

    suspend fun addPermissionToUser(userId: String, permission: Int): Boolean

    suspend fun removePermissionFromUser(userId: String, permission: Int): Boolean

    suspend fun getUsers(page: Int, limit: Int, searchTerm: String): List<UserManagement>

}

@Single
class UserManagementUseCase(private val dataBaseGateway: IDataBaseGateway) : IUserManagementUseCase {

    override suspend fun addPermissionToUser(userId: String, permission: Int): Boolean {
        val userPermission = dataBaseGateway.getUserPermission(userId)
        val newPermission = grantPermission(userPermission, permission)
        return dataBaseGateway.addPermissionToUser(userId, newPermission)
    }

    override suspend fun removePermissionFromUser(userId: String, permission: Int): Boolean {
        val userPermission = dataBaseGateway.getUserPermission(userId)
        val removePermission = revokePermission(userPermission, permission)
        return dataBaseGateway.removePermissionFromUser(userId, removePermission)
    }

    override suspend fun getUsers(page: Int, limit: Int, searchTerm: String): List<UserManagement> {
        return dataBaseGateway.getUsers(page, limit, searchTerm)
    }

    private fun grantPermission(currentPermissions: Int, permissionToAdd: Int): Int {
        return currentPermissions or permissionToAdd
    }

    private fun revokePermission(currentPermissions: Int, permissionToRemove: Int): Int {
        return currentPermissions and permissionToRemove.inv()
    }

}