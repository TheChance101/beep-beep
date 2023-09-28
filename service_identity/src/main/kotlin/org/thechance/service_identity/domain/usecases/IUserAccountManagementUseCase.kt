package org.thechance.service_identity.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.entity.UserInfo
import org.thechance.service_identity.domain.entity.UserManagement
import org.thechance.service_identity.domain.entity.Wallet
import org.thechance.service_identity.domain.gateway.IDataBaseGateway
import org.thechance.service_identity.domain.security.HashingService
import org.thechance.service_identity.domain.usecases.validation.IUserInfoValidationUseCase
import org.thechance.service_identity.domain.usecases.validation.IWalletBalanceValidationUseCase
import org.thechance.service_identity.domain.util.*

interface IUserAccountManagementUseCase {

    suspend fun createUser(password: String?, user: UserInfo): UserManagement

    suspend fun deleteUser(id: String): Boolean

    suspend fun updateUser(id: String, fullName: String? = null, email: String? = null): UserManagement

    suspend fun getUser(id: String): User

    suspend fun login(username: String, password: String, applicationId: String): Boolean

    suspend fun getUserByUsername(username: String): UserManagement

}

@Single
class UserAccountManagementUseCase(
    private val dataBaseGateway: IDataBaseGateway,
    private val userInfoValidationUseCase: IUserInfoValidationUseCase,
    private val hashingService: HashingService
) : IUserAccountManagementUseCase {

    override suspend fun createUser(password: String?, user: UserInfo): UserManagement {
        userInfoValidationUseCase.validateUserInformation(password = password, user = user)
        val userCountry = getUserCountry(user.phone)
        val saltedHash = hashingService.generateSaltedHash(password!!)
        val newUser = dataBaseGateway.createUser(saltedHash, country = userCountry.name, user = user)
        dataBaseGateway.createWallet(newUser.id, currency = userCountry.currency)
        dataBaseGateway.addAddress(newUser.id, user.addresses.first())
        return newUser
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

    override suspend fun updateUser(id: String, fullName: String?, email: String?): UserManagement {
        userInfoValidationUseCase.validateUpdateUserInformation(fullName, email)
        return dataBaseGateway.updateUser(id, fullName, email)
    }

    override suspend fun getUser(id: String): User {
        return dataBaseGateway.getUserById(id)
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
        map[ApplicationId.TAXI_DRIVER] = Pair(System.getenv(ApplicationId.TAXI_DRIVER).toString(), Role.TAXI_DRIVER)
        map[ApplicationId.DELIVERY] = Pair(System.getenv(ApplicationId.DELIVERY).toString(), Role.DELIVERY)
        map[ApplicationId.SUPPORT] = Pair(System.getenv(ApplicationId.SUPPORT).toString(), Role.SUPPORT)
        return map
    }

    private fun getUserCountry(phone: String): CountryCurrency {
        val matchingEntry = countryMap.entries.find { phone.startsWith(it.value) }
        val countryName = matchingEntry?.key ?: "Unknown"
        return CountryCurrency.valueOf(countryName)
    }

}