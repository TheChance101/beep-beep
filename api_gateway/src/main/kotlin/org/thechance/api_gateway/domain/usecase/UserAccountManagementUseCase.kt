package org.thechance.api_gateway.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.gateway.ApiGateway
import org.thechance.api_gateway.data.model.identity.UserManagementResource
import org.thechance.api_gateway.data.security.hashing.SaltedHash

/**
 * Created by Aziza Helmy on 8/14/2023.
 */
interface IUserAccountManagementUseCase {

    suspend fun createUser(fullName: String, username: String, password: String, email: String): Boolean
    suspend fun loginUser(userName: String, password: String): Boolean
    suspend fun getUser(id: String): UserManagementResource
    suspend fun securePassword(password: String): SaltedHash
    suspend fun deleteUser(id: String): Boolean

}

@Single
class UserAccountManagementUseCase(
    private val apiGateway: ApiGateway,
    private val userInfoValidationUseCase: IUserInfoValidationUseCase,
) : IUserAccountManagementUseCase {
    override suspend fun createUser(fullName: String, username: String, password: String, email: String): Boolean {
        userInfoValidationUseCase.validateUserInformation(fullName, username, password, email)
        return apiGateway.createUser(fullName, username, password, email)
    }

    override suspend fun loginUser(userName: String, password: String): Boolean {
        userInfoValidationUseCase.validateLoginInformation(userName, password)
        return apiGateway.loginUser(userName, password)
    }

    override suspend fun getUser(id: String): UserManagementResource {
        TODO("Not yet implemented")
    }

    override suspend fun securePassword(password: String): SaltedHash {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(id: String): Boolean {
        TODO("Not yet implemented")
    }

}