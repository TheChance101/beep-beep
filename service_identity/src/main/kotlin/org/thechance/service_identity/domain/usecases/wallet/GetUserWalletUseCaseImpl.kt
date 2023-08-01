package org.thechance.service_identity.domain.usecases.wallet

import org.thechance.service_identity.data.geteway.WalletGateWayImpl
import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.WalletGateWay
import org.thechance.service_identity.entity.Wallet

@Single
class GetUserWalletUseCaseImpl (
    private val walletGateWay: WalletGateWay
) : GetUserWalletUseCase {
    override suspend fun invoke(id: String): Wallet {
        return walletGateWay.getWalletByUserId(id)
    }
}
