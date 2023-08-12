package org.thechance.service_identity.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.ManagedUser
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.gateway.IDataBaseGateway

interface IUserManagementUseCase {

    suspend fun addPermissionToUser(userId: String, permissionId: Int): Boolean

    suspend fun removePermissionFromUser(userId: String, permissionId: Int): Boolean

    suspend fun getUserPermissions(userId: String): List<Permission>

    suspend fun getUsers(page: Int, limit: Int): List<ManagedUser>

    suspend fun searchUsers(searchTerm: String): List<ManagedUser>

}

@Single
class UserManagementUseCase(private val dataBaseGateway: IDataBaseGateway) : IUserManagementUseCase {

    override suspend fun addPermissionToUser(userId: String, permissionId: Int): Boolean {
        return dataBaseGateway.addPermissionToUser(userId, permissionId)
    }

    override suspend fun removePermissionFromUser(userId: String, permissionId: Int): Boolean {
        return dataBaseGateway.removePermissionFromUser(userId, permissionId)
    }

    override suspend fun getUserPermissions(userId: String): List<Permission> {
        return dataBaseGateway.getUserPermissions(userId)
    }

    override suspend fun getUsers(page: Int, limit: Int): List<ManagedUser> {
        return dataBaseGateway.getUsers(page, limit)
    }

    override suspend fun searchUsers(searchTerm: String): List<ManagedUser> {
        return dataBaseGateway.searchUser(searchTerm)
    }

}