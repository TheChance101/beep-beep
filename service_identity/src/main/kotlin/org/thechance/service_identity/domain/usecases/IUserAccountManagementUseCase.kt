package org.thechance.service_identity.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.entity.UserManagement
import org.thechance.service_identity.domain.entity.Wallet
import org.thechance.service_identity.domain.gateway.IDataBaseGateway
import org.thechance.service_identity.domain.security.HashingService
import org.thechance.service_identity.domain.usecases.validation.IUserInfoValidationUseCase
import org.thechance.service_identity.domain.usecases.validation.IWalletBalanceValidationUseCase
import org.thechance.service_identity.domain.util.*

interface IUserAccountManagementUseCase {

    suspend fun createUser(fullName: String, username: String, password: String, email: String): UserManagement

    suspend fun deleteUser(id: String): Boolean

    suspend fun updateUser(
        id: String, fullName: String? = null, username: String? = null, password: String? = null, email: String? = null
    ): Boolean

    suspend fun getUser(id: String): User

    suspend fun addToWallet(userId: String, amount: Double): Wallet

    suspend fun subtractFromWallet(userId: String, amount: Double): Wallet

    suspend fun login(username: String, password: String, applicationId: String): Boolean

    suspend fun getUserByUsername(username: String): UserManagement

}

@Single
class UserAccountManagementUseCase(
    private val dataBaseGateway: IDataBaseGateway,
    private val walletBalanceValidationUseCase: IWalletBalanceValidationUseCase,
    private val userInfoValidationUseCase: IUserInfoValidationUseCase,
    private val hashingService: HashingService
) : IUserAccountManagementUseCase {

    override suspend fun createUser(
        fullName: String, username: String, password: String, email: String,
    ): UserManagement {
        userInfoValidationUseCase.validateUserInformation(fullName, username, password, email)

        val saltedHash = hashingService.generateSaltedHash(password)
        return dataBaseGateway.createUser(saltedHash, fullName, username, email)
    }

    override suspend fun getUserByUsername(username: String): UserManagement {
        return dataBaseGateway.getUserByUsername(username)
    }

    override suspend fun login(username: String, password: String, applicationId: String): Boolean {
        val saltedHash = dataBaseGateway.getSaltedHash(username)
        return if (hashingService.verify(password, saltedHash)) {
            val userPermission = dataBaseGateway.getUserPermissionByUsername(username)
            if (verifyPermissionToLogin(userPermission, applicationId)) {
                true
            } else {
                throw InvalidCredentialsException(INVALID_PERMISSION)
            }
        } else throw InvalidCredentialsException(INVALID_CREDENTIALS)
    }

    override suspend fun deleteUser(id: String): Boolean {
        if (dataBaseGateway.isUserDeleted(id)) {
            throw ResourceNotFoundException(NOT_FOUND)
        }
        return dataBaseGateway.deleteUser(id)
    }

    override suspend fun updateUser(
        id: String, fullName: String?, username: String?, password: String?, email: String?
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

    override suspend fun addToWallet(userId: String, amount: Double): Wallet {
        walletBalanceValidationUseCase.validateWalletBalance(amount)
        return dataBaseGateway.addToWallet(userId, amount)
    }

    override suspend fun subtractFromWallet(userId: String, amount: Double): Wallet {
        walletBalanceValidationUseCase.validateWalletBalance(amount)
        if (amount > dataBaseGateway.getWalletBalance(userId).walletBalance) {
            throw InsufficientFundsException(INSUFFICIENT_FUNDS)
        }
        return dataBaseGateway.subtractFromWallet(userId, amount)
    }

    private fun verifyPermissionToLogin(userPermission: Int, applicationId: String): Boolean {
        val applicationIds = getApplicationIdFromEnvironment()
        return applicationIds.filterValues { pair -> pair.first == applicationId && (pair.second and userPermission) == pair.second }
            .isNotEmpty()
    }

    private fun getApplicationIdFromEnvironment(): HashMap<String, Pair<String, Int>> {
        val map = hashMapOf<String, Pair<String, Int>>()
        map[ApplicationId.END_USER] = Pair(System.getenv(ApplicationId.END_USER).toString(), Role.END_USER)
        map[ApplicationId.RESTAURANT] = Pair(System.getenv(ApplicationId.RESTAURANT).toString(), Role.RESTAURANT_OWNER)
        map[ApplicationId.DASHBOARD] = Pair(System.getenv(ApplicationId.DASHBOARD).toString(), Role.DASHBOARD_ADMIN)
//        map[ApplicationId.TAXI_DRIVER] = Pair(System.getenv(ApplicationId.TAXI_DRIVER).toString(), Role.TAXI_DRIVER)
//        map[ApplicationId.DELIVERY] = Pair(System.getenv(ApplicationId.DELIVERY).toString(), Role.DELIVERY)
//        map[ApplicationId.SUPPORT] = Pair(System.getenv(ApplicationId.SUPPORT).toString(), Role.SUPPORT)
        return map
    }

}