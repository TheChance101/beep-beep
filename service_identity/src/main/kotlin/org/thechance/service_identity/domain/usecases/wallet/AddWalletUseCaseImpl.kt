package org.thechance.service_identity.domain.usecases.wallet

import org.thechance.service_identity.data.geteway.WalletGateWayImpl
import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.WalletGateWay
import org.thechance.service_identity.entity.Wallet

@Single
class AddWalletUseCaseImpl (
    private val walletGateWay: WalletGateWay
) : AddWalletUseCase {
    override suspend fun invoke(wallet: Wallet) : Boolean {
      return  walletGateWay.createWallet(wallet)
    }
}
