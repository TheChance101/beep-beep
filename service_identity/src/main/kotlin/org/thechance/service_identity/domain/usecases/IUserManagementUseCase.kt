package org.thechance.service_identity.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.entity.UserManagement
import org.thechance.service_identity.domain.entity.UserOptions
import org.thechance.service_identity.domain.gateway.IDataBaseGateway
import org.thechance.service_identity.domain.util.INVALID_PERMISSION
import org.thechance.service_identity.domain.util.RequestValidationException

interface IUserManagementUseCase {

    suspend fun updateUserPermission(userId: String, permissions: List<Int>): UserManagement

    suspend fun getUsers(options: UserOptions): List<UserManagement>

    suspend fun getNumberOfUsers(): Long

    suspend fun getLastRegisterUser(limit: Int): List<UserManagement>

    suspend fun isUserExisted(userId: String): Boolean

    //TODO DELETE THIS LETTER
    suspend fun deleteCollections(): Boolean

}

@Single
class UserManagementUseCase(private val dataBaseGateway: IDataBaseGateway) : IUserManagementUseCase {

    override suspend fun updateUserPermission(userId: String, permissions: List<Int>): UserManagement {
        isValidPermission(permissions)
        val permission = permissions.reduce { acc, i -> acc or i }
        return dataBaseGateway.updateUserPermission(userId, permission)
    }

    override suspend fun getUsers(options: UserOptions): List<UserManagement> {
        return dataBaseGateway.getUsers(options)
    }

    override suspend fun getNumberOfUsers(): Long {
        return dataBaseGateway.getNumberOfUsers()
    }

    override suspend fun getLastRegisterUser(limit: Int): List<UserManagement> {
        return dataBaseGateway.getLastRegisterUser(limit)
    }

    override suspend fun isUserExisted(userId: String): Boolean {
        return dataBaseGateway.isUserExisted(userId)
    }

    override suspend fun deleteCollections(): Boolean {
        return dataBaseGateway.deleteCollections()
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