package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Country
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Permission
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.IUserLocalGateway
import org.thechance.common.domain.getway.IUsersGateway
import org.thechance.common.presentation.users.UserScreenUiState

interface IUsersManagementUseCase {

    suspend fun getUserInfo(): String

    suspend fun getUsers(
        query: String? = null,
        byPermissions: List<Permission>,
        byCountries: List<Country>,
        page: Int,
        numberOfUsers: Int
    ): DataWrapper<User>

    suspend fun deleteUser(userId: String): Boolean
    suspend fun updateUserPermissions(userId: String, permissions: List<Permission>): User

    suspend fun getLastRegisteredUsers(limit: Int = 4): List<User>

}

class UsersManagementUseCase(
    private val userGateway: IUsersGateway,
    private val userLocalGateway: IUserLocalGateway
) : IUsersManagementUseCase {

    override suspend fun getUsers(
        query: String?,
        byPermissions: List<Permission>,
        byCountries: List<Country>,
        page: Int,
        numberOfUsers: Int,
    ): DataWrapper<User> {
        return userGateway.getUsers(query, byPermissions, byCountries, page, numberOfUsers)
    }

    override suspend fun deleteUser(userId: String): Boolean {
        return userGateway.deleteUser(userId)
    }

    override suspend fun getLastRegisteredUsers(limit: Int): List<User> {
        return userGateway.getLastRegisteredUsers(limit)
    }

    override suspend fun updateUserPermissions(
        userId: String,
        permissions: List<Permission>
    ): User {
        return userGateway.updateUserPermissions(userId, permissions)
    }

    override suspend fun getUserInfo(): String {
        return userLocalGateway.getUserName()
    }

}