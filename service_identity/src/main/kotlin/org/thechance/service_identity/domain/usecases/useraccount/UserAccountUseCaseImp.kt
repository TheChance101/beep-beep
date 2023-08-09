package org.thechance.service_identity.domain.usecases.useraccount

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.entity.ManagedUser
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.gateway.DataBaseGateway

@Single
class UserAccountUseCaseImp(
    private val dataBaseGateway: DataBaseGateway,
) : UserAccountUseCase {

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

    override suspend fun getUsers(): List<ManagedUser> {
        return dataBaseGateway.getUsers()
    }

    override suspend fun getUser(id: String): User {
        return dataBaseGateway.getUserById(id)
    }

    // endregion

    // region address
    override suspend fun addAddress(userId: String, address: Address): Boolean {
        return dataBaseGateway.addAddress(userId, address)
    }

    override suspend fun deleteAddress(id: String): Boolean {
        return dataBaseGateway.deleteAddress(id)
    }

    override suspend fun updateAddress(id: String, address: Address): Boolean {
        return dataBaseGateway.updateAddress(id, address)
    }

    override suspend fun getAddress(id: String): Address {
        return dataBaseGateway.getAddress(id)
    }

    override suspend fun getUserAddresses(userId: String): List<Address> {
        return dataBaseGateway.getUserAddresses(userId)
    }

    //endregion
}

