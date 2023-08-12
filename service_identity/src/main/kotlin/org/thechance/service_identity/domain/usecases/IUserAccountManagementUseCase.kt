package org.thechance.service_identity.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_identity.data.geteway.security.HashingService
import org.thechance.service_identity.data.security.hashing.SaltedHash
import org.thechance.service_identity.domain.entity.CreateUserRequest
import org.thechance.service_identity.domain.entity.InsufficientFundsException
import org.thechance.service_identity.domain.entity.UpdateUserRequest
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.gateway.IDataBaseGateway
import org.thechance.service_identity.domain.usecases.validation.IUserInfoValidationUseCase
import org.thechance.service_identity.domain.usecases.validation.IWalletBalanceValidationUseCase
import org.thechance.service_identity.domain.util.INSUFFICIENT_FUNDS

interface IUserAccountManagementUseCase {

    suspend fun createUser(user: CreateUserRequest): Boolean
    suspend fun securePassword(password: String): SaltedHash

    suspend fun deleteUser(id: String): Boolean

    suspend fun updateUser(id: String, user: UpdateUserRequest): Boolean

    suspend fun getUser(id: String): User

    suspend fun addToWallet(userId: String, amount: Double): Boolean

    suspend fun subtractFromWallet(userId: String, amount: Double): Boolean

}

@Single
class UserAccountManagementUseCase(
    private val dataBaseGateway: IDataBaseGateway,
    private val walletBalanceValidationUseCase: IWalletBalanceValidationUseCase,
    private val userInfoValidationUseCase: IUserInfoValidationUseCase,
    private val hashingService: HashingService
) : IUserAccountManagementUseCase {

    override suspend fun createUser(user: CreateUserRequest): Boolean {
        userInfoValidationUseCase.validateUserInformation(user)
        val saltedHash = securePassword(user.password)
        return dataBaseGateway.createUser(saltedHash,user)
    }

    override suspend fun securePassword(password: String): SaltedHash {
        return hashingService.generateSaltedHash(password)
    }

    override suspend fun deleteUser(id: String): Boolean {
        return dataBaseGateway.deleteUser(id)
    }

    override suspend fun updateUser(id: String, user: UpdateUserRequest): Boolean {
        userInfoValidationUseCase.validateUpdateUserInformation(user)
        return dataBaseGateway.updateUser(id, user)
    }

    override suspend fun getUser(id: String): User {
        return dataBaseGateway.getUserById(id)
    }

    override suspend fun addToWallet(userId: String, amount: Double): Boolean {
        walletBalanceValidationUseCase.validateWalletBalance(amount)
        return dataBaseGateway.addToWallet(userId, amount)
    }

    override suspend fun subtractFromWallet(userId: String, amount: Double): Boolean {
        walletBalanceValidationUseCase.validateWalletBalance(amount)
        if (amount > dataBaseGateway.getWalletBalance(userId)) {
            throw InsufficientFundsException(INSUFFICIENT_FUNDS)
        }
        return dataBaseGateway.subtractFromWallet(userId, amount)
    }

}