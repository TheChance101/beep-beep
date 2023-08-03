package org.thechance.service_identity.domain.usecases.wallet

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Wallet
import org.thechance.service_identity.domain.gateway.WalletGateWay

@Single
class AddWalletUseCaseImpl(
    private val walletGateWay: WalletGateWay,
) : AddWalletUseCase {
    override suspend fun invoke(wallet: Wallet): Boolean {
        return walletGateWay.createWallet(wallet)
    }
}
