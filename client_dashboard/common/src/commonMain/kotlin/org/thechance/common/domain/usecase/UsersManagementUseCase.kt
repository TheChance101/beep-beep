package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Permission
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.IUsersGateway

interface IUsersManagementUseCase {

    suspend fun getUserInfo(): String

    suspend fun getUsers(
        query: String? = null,
        byPermissions: List<Permission>,
        byCountries: List<String>,
        page: Int,
        numberOfUsers: Int
    ): DataWrapper<User>

    suspend fun deleteUser(userId: String): Boolean

    suspend fun getLastRegisteredUsers(limit: Int = 4): List<User>

}

class UsersManagementUseCase(
    private val userGateway: IUsersGateway
) : IUsersManagementUseCase {

    override suspend fun getUsers(
        query: String?,
        byPermissions: List<Permission>,
        byCountries: List<String>,
        page: Int,
        numberOfUsers: Int,
    ): DataWrapper<User> {
        return userGateway.getUsers(query, byPermissions, byCountries, page, numberOfUsers)
    }

    override suspend fun deleteUser(id: String): Boolean {
        return userGateway.deleteUser(id)
    }

    override suspend fun getLastRegisteredUsers(limit: Int): List<User> {
        return userGateway.getLastRegisteredUsers(limit)
    }

    override suspend fun getUserInfo(): String {
        return userGateway.getUserData()
    }

}