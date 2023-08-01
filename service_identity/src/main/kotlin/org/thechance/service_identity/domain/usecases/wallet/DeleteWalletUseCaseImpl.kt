package org.thechance.service_identity.domain.usecases.wallet

import org.thechance.service_identity.data.geteway.WalletGateWayImpl
import org.koin.core.annotation.Single

@Single
class DeleteWalletUseCaseImpl (
    private val walletGateWay: WalletGateWayImpl
) : DeleteWalletUseCase {
    override suspend fun invoke(id : String) : Boolean{
      return  walletGateWay.deleteWallet(id)
    }
}