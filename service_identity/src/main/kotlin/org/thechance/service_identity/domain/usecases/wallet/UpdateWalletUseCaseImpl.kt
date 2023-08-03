package org.thechance.service_identity.domain.usecases.wallet

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Wallet
import org.thechance.service_identity.domain.gateway.WalletGateWay

@Single
class UpdateWalletUseCaseImpl(
    private val walletGateWay: WalletGateWay
) : UpdateWalletUseCase {
    override suspend fun invoke(id:String,wallet: Wallet) : Boolean{
        return walletGateWay.updateWallet(id,wallet)
    }
}