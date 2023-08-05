package org.thechance.service_identity.domain.usecases.useraccount

import org.thechance.service_identity.domain.entity.User

import org.thechance.service_identity.domain.entity.Address

import org.thechance.service_identity.domain.entity.Wallet

interface UserAccountUseCase {


    // region address
    suspend fun addAddress(address: Address): Boolean

    suspend fun deleteAddress(id: String): Boolean

    suspend fun updateAddress(id: String, address: Address): Boolean

    suspend fun getAddress(id: String): Address

    suspend fun getUserAddresses(userId: String): List<Address>

    //endregion

    // region user

    suspend fun createUser(user: User): Boolean
    suspend fun deleteUser(id: String): Boolean
    suspend fun updateUser(id: String, user: User): Boolean
    suspend fun getUsers(): List<User>
    suspend fun getUser(id: String): User

    // endregion

}