package org.thechance.service_identity.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.UserManagement
import org.thechance.service_identity.domain.gateway.IDataBaseGateway

interface IUserManagementUseCase {

//    suspend fun addPermissionToUser(userId: String, permission: Int): UserManagement
//
//    suspend fun removePermissionFromUser(userId: String, permission: Int): UserManagement

    suspend fun  updateUserPermission(userId: String, permissions: List<Int>): UserManagement

    suspend fun getUsers(page: Int, limit: Int, searchTerm: String): List<UserManagement>

    suspend fun getNumberOfUsers(): Long

    suspend fun  getLastRegisterUser(limit:Int):List<UserManagement>

}

@Single
class UserManagementUseCase(private val dataBaseGateway: IDataBaseGateway) : IUserManagementUseCase {

    override suspend fun updateUserPermission(
        userId: String,
        permissions: List<Int>
    ): UserManagement {
        return dataBaseGateway.updateUserPermission(userId, permissions)
    }

    override suspend fun getUsers(page: Int, limit: Int, searchTerm: String): List<UserManagement> {
        return dataBaseGateway.getUsers(page, limit, searchTerm)
    }

    override suspend fun getNumberOfUsers(): Long {
        return dataBaseGateway.getNumberOfUsers()
    }

    override suspend fun getLastRegisterUser(limit: Int): List<UserManagement> {
        return dataBaseGateway.getLastRegisterUser(limit)
    }

    private fun grantPermission(currentPermissions: Int, permissionToAdd: Int): Int {
        return currentPermissions or permissionToAdd
    }

    private fun revokePermission(currentPermissions: Int, permissionToRemove: Int): Int {
        return currentPermissions and permissionToRemove.inv()
    }

}