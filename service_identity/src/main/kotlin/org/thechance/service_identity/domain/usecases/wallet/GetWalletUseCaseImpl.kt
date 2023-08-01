package org.thechance.service_identity.domain.usecases.wallet

import org.thechance.service_identity.data.geteway.WalletGateWayImpl
import org.koin.core.annotation.Single
import org.thechance.service_identity.entity.Wallet

@Single
class GetWalletUseCaseImpl (
    private val walletGateWay: WalletGateWayImpl
) : GetWalletUseCase {
    override suspend fun invoke(id: String): Wallet {
        return walletGateWay.getWalletById(id)
    }
}