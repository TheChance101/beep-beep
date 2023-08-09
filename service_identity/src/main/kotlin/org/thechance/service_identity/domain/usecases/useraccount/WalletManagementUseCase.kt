package org.thechance.service_identity.domain.usecases.useraccount

import org.thechance.service_identity.domain.entity.Wallet

interface WalletManagementUseCase {
    suspend fun getWallet(walletId: String): Wallet
    suspend fun createWallet(wallet: Wallet): Boolean
    suspend fun updateWallet(walletId: String, wallet: Wallet): Boolean

}