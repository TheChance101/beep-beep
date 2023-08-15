package org.thechance.service_identity.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.security.HashingService
import org.thechance.service_identity.domain.entity.InsufficientFundsException
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.gateway.IDataBaseGateway
import org.thechance.service_identity.domain.usecases.validation.IUserInfoValidationUseCase
import org.thechance.service_identity.domain.usecases.validation.IWalletBalanceValidationUseCase
import org.thechance.service_identity.domain.util.INSUFFICIENT_FUNDS

interface IUserAccountManagementUseCase {

    suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String,
    ): Boolean

    suspend fun deleteUser(id: String): Boolean

    suspend fun updateUser(
        id: String, fullName: String? = null,
        username: String? = null,
        password: String? = null,
        email: String? = null
    ): Boolean

    suspend fun getUser(id: String): User

    suspend fun addToWallet(userId: String, amount: Double): Boolean

    suspend fun subtractFromWallet(userId: String, amount: Double): Boolean

    suspend fun login(username: String, password: String): Boolean

    suspend fun saveUserTokens(userId: String, accessToken: String, refreshToken: String, expirationDate: Int): Boolean
}

@Single
class UserAccountManagementUseCase(
    private val dataBaseGateway: IDataBaseGateway,
    private val walletBalanceValidationUseCase: IWalletBalanceValidationUseCase,
    private val userInfoValidationUseCase: IUserInfoValidationUseCase,
    private val hashingService: HashingService
) : IUserAccountManagementUseCase {

    override suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String,
    ): Boolean {
        userInfoValidationUseCase.validateUserInformation(fullName, username, password, email)
        val saltedHash = hashingService.generateSaltedHash(password)
        return dataBaseGateway.createUser(saltedHash, fullName, username, email)
    }

    override suspend fun login(username: String, password: String): Boolean {
        val saltedHash = dataBaseGateway.getSaltedHash(username)
        return hashingService.verify(password, saltedHash)
    }

    override suspend fun saveUserTokens(
        userId: String,
        accessToken: String,
        refreshToken: String,
        expirationDate: Int
    ): Boolean {
        return dataBaseGateway.saveUserTokens(userId, accessToken, refreshToken, expirationDate)
    }


    override suspend fun deleteUser(id: String): Boolean {
        return dataBaseGateway.deleteUser(id)
    }

    override suspend fun updateUser(
        id: String, fullName: String?,
        username: String?,
        password: String?,
        email: String?
    ): Boolean {
        userInfoValidationUseCase.validateUpdateUserInformation(fullName, username, password, email)
        val saltedHash = password?.let {
            hashingService.generateSaltedHash(it)
        }
        return dataBaseGateway.updateUser(id, saltedHash, fullName, username, email)
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