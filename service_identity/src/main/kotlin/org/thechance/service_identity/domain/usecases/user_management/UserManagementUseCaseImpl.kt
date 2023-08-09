package org.thechance.service_identity.domain.usecases.user_management

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.gateway.DataBaseGateway

@Single
class UserManagementUseCaseImpl(private val dataBaseGateway: DataBaseGateway) : UserManagementUseCase {

    override suspend fun addPermissionToUser(userId: String, permissionId: Int): Boolean {
        return dataBaseGateway.addPermissionToUser(userId, permissionId)
    }

    override suspend fun removePermissionFromUser(userId: String, permissionId: Int): Boolean {
        return dataBaseGateway.removePermissionFromUser(userId, permissionId)
    }

    override suspend fun getUserPermissions(userId: String): List<Permission> {
        return dataBaseGateway.getUserPermissions(userId)
    }

    override suspend fun getUserById(id: String): User {
        return dataBaseGateway.getUserById(id)
    }

    override suspend fun getUsersList(fullName: String, username: String): List<User> {
        return dataBaseGateway.getUsers(fullName, username)
    }

}