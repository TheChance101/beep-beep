package org.thechance.service_identity.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.UserManagement
import org.thechance.service_identity.domain.gateway.IDataBaseGateway
import org.thechance.service_identity.domain.util.INVALID_PERMISSION
import org.thechance.service_identity.domain.util.RequestValidationException

interface IUserManagementUseCase {

    suspend fun  updateUserPermission(userId: String, permissions: List<Int>): UserManagement

    suspend fun getUsers(page: Int, limit: Int, searchTerm: String): List<UserManagement>

    suspend fun getNumberOfUsers(): Long

    suspend fun getLastRegisterUser(limit: Int): List<UserManagement>

    suspend fun searchUsers(searchTerm: String, filterByPermission: List<Int>): List<UserManagement>

}

@Single
class UserManagementUseCase(private val dataBaseGateway: IDataBaseGateway) : IUserManagementUseCase {

    override suspend fun updateUserPermission(userId: String, permissions: List<Int>): UserManagement {
        isValidPermission(permissions)
        val permission = permissions.reduce { acc, i -> acc or i }
        return dataBaseGateway.updateUserPermission(userId, permission)
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

    override suspend fun searchUsers(
        searchTerm: String,
        filterByPermission: List<Int>
    ): List<UserManagement> {
        return dataBaseGateway.searchUsers(searchTerm, filterByPermission)
    }

    private fun isValidPermission(permission: List<Int>) {
        val reasons = mutableListOf<String>()
        if (permission.isEmpty()) {
            reasons.add(INVALID_PERMISSION)
        }
        if (reasons.isNotEmpty()) {
            throw RequestValidationException(reasons)
        }
    }
}