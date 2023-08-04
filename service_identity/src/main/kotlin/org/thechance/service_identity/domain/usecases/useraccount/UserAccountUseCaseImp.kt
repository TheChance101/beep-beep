package org.thechance.service_identity.domain.usecases.useraccount

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.entity.Wallet
import org.thechance.service_identity.domain.gateway.DataBaseGateway

@Single
class UserAccountUseCaseImp(
    private val dataBaseGateway: DataBaseGateway
) : UserAccountUseCase {

    // region: wallet
    override suspend fun getWallet(walletId: String): Wallet {
        return dataBaseGateway.getWallet(walletId)
    }

    override suspend fun createWallet(wallet: Wallet): Boolean {
        return dataBaseGateway.createWallet(wallet)
    }

    override suspend fun updateWallet(walletId: String, wallet: Wallet): Boolean {
        return dataBaseGateway.updateWallet(walletId,wallet)
    }
    // endregion: wallet



    // region user
    override suspend fun createUser(user: User): Boolean {
       return dataBaseGateway.createUser(user)
    }

    override suspend fun deleteUser(id: String): Boolean {
       return dataBaseGateway.deleteUser(id)
    }

    override suspend fun updateUser(id: String, user: User): Boolean {
        return dataBaseGateway.updateUser(id, user)
    }

    override suspend fun getUsers(): List<User> {
        return dataBaseGateway.getUsers()
    }

    override suspend fun getUser(id: String): User {
        return dataBaseGateway.getUserById(id) ?: throw Throwable("Invalid id")
    }

    // endregion

    // region address
    override suspend fun addAddress(address: Address): Boolean {
        return dataBaseGateway.addAddress(address)
    }

    override suspend fun deleteAddress(id: String): Boolean {
        return dataBaseGateway.deleteAddress(id)
    }

    override suspend fun updateAddress(id: String, address: Address): Boolean {
        return dataBaseGateway.updateAddress(id, address)
    }

    override suspend fun getAddress(id: String): Address {
        return dataBaseGateway.getAddress(id) ?: throw Throwable()
    }

    override suspend fun getUserAddresses(userId: String): List<Address> {
        return dataBaseGateway.getUserAddresses(userId)
    }

    //endregion
}