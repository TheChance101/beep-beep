package org.thechance.service_identity.domain.usecases.wallet

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Wallet
import org.thechance.service_identity.domain.gateway.WalletGateWay

@Single
class GetWalletUseCaseImpl (
    private val walletGateWay: WalletGateWay
) : GetWalletUseCase {
    override suspend fun invoke(id: String): Wallet {
        return walletGateWay.getWalletById(id)
    }
}