package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Permission
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.IUsersGateway

interface IManageUsersUseCase {

    suspend fun deleteUser(userId: String): Boolean

    suspend fun updateUserPermissions(userId: String, permissions: List<Permission>): User

}

class ManageUsersUseCase(
    private val userGateway: IUsersGateway,
) : IManageUsersUseCase {

    override suspend fun deleteUser(userId: String): Boolean {
        return userGateway.deleteUser(userId)
    }

    override suspend fun updateUserPermissions(
        userId: String,
        permissions: List<Permission>
    ): User {
        return userGateway.updateUserPermissions(userId, permissions)
    }

}