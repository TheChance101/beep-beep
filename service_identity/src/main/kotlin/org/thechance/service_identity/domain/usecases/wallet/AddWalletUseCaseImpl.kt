package org.thechance.service_identity.domain.usecases.wallet

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Wallet
import org.thechance.service_identity.domain.gateway.WalletGateWay
import org.thechance.service_identity.domain.usecases.user_details.AddWalletToUserUseCase

@Single
class AddWalletUseCaseImpl(
    private val walletGateWay: WalletGateWay,
    private val addWalletToUser: AddWalletToUserUseCase
) : AddWalletUseCase {
    override suspend fun invoke(wallet: Wallet): Boolean {
        addWalletToUser(wallet.userId, wallet.id)
        return walletGateWay.createWallet(wallet)
    }
}
