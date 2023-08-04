package org.thechance.service_identity.domain.usecases.useraccount

import org.thechance.service_identity.domain.entity.Address

import org.thechance.service_identity.domain.entity.Wallet

interface UserAccountUseCase {

    // region: wallet
    suspend fun getWallet(walletId: String): Wallet
    suspend fun createWallet(wallet: Wallet): Boolean
    suspend fun updateWallet(walletId: String, wallet: Wallet): Boolean

    // endregion: wallet


    // region address
    suspend fun addAddress(address: Address): Boolean

    suspend fun deleteAddress(id: String): Boolean

    suspend fun updateAddress(id: String, address: Address): Boolean

    suspend fun getAddress(id: String): Address

    suspend fun getUserAddresses(userId: String): List<Address>

    //endregion
}