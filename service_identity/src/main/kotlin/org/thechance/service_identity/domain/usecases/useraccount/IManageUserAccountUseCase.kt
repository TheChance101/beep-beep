package org.thechance.service_identity.domain.usecases.useraccount

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.InsufficientFundsException
import org.thechance.service_identity.domain.entity.ManagedUser
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.gateway.DataBaseGateway
import org.thechance.service_identity.domain.usecases.util.INSUFFICIENT_FUNDS
import org.thechance.service_identity.domain.usecases.validation.IValidateUserInfoUseCase
import org.thechance.service_identity.domain.usecases.validation.IValidateWalletBalanceUseCase

interface IManageUserAccountUseCase {

    suspend fun createUser(user: User): Boolean

    suspend fun deleteUser(id: String): Boolean

    suspend fun updateUser(id: String, user: User): Boolean

    suspend fun getUsers(): List<ManagedUser>

    suspend fun getUser(id: String): User

    suspend fun addToWallet(userId: String, amount: Double): Boolean

    suspend fun subtractFromWallet(userId: String, amount: Double): Boolean

}

@Single
class IManageUserAccountUseCaseImp(
    private val dataBaseGateway: DataBaseGateway,
    private val validateWalletBalance: IValidateWalletBalanceUseCase,
    private val validateUserInfo: IValidateUserInfoUseCase
) : IManageUserAccountUseCase {

    override suspend fun createUser(user: User): Boolean {
        validateUserInfo.validateUserInformation(user)
        return dataBaseGateway.createUser(user)
    }

    override suspend fun deleteUser(id: String): Boolean {
        return dataBaseGateway.deleteUser(id)
    }

    override suspend fun updateUser(id: String, user: User): Boolean {
        validateUserInfo.validateUpdateUserInformation(user)
        return dataBaseGateway.updateUser(id, user)
    }

    override suspend fun getUsers(): List<ManagedUser> {
        return dataBaseGateway.getUsers()
    }

    override suspend fun getUser(id: String): User {
        return dataBaseGateway.getUserById(id)
    }

    override suspend fun addToWallet(userId: String, amount: Double): Boolean {
        validateWalletBalance.validateWalletBalance(amount)
        return dataBaseGateway.addToWallet(userId, amount)
    }

    override suspend fun subtractFromWallet(userId: String, amount: Double): Boolean {
        validateWalletBalance.validateWalletBalance(amount)
        if (amount > dataBaseGateway.getWalletBalance(userId)) {
            throw InsufficientFundsException(INSUFFICIENT_FUNDS)
        }
        return dataBaseGateway.subtractFromWallet(userId, amount)
    }

}