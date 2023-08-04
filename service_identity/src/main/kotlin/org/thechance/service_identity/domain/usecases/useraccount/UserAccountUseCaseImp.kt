package org.thechance.service_identity.domain.usecases.useraccount

import org.thechance.service_identity.domain.entity.Wallet
import org.thechance.service_identity.domain.gateway.DataBaseGateway

class UserAccountUseCaseImp(
    private val dataBaseGateway: DataBaseGateway
) : UserAccountUseCase {
    override suspend fun getWallet(walletId: String): Wallet {
        return dataBaseGateway.getWallet(walletId)
    }

    override suspend fun createWallet(wallet: Wallet): Boolean {
        return dataBaseGateway.createWallet(wallet)
    }

    override suspend fun updateWallet(walletId: String, wallet: Wallet): Boolean {
        return dataBaseGateway.updateWallet(walletId,wallet)
    }
}