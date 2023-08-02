package org.thechance.service_identity.domain.usecases.wallet

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.WalletGateWay
import org.thechance.service_identity.domain.usecases.user_details.DeleteUserWalletUseCase

@Single
class DeleteWalletUseCaseImpl(
    private val walletGateWay: WalletGateWay,
    private val deleteUserWallet: DeleteUserWalletUseCase
) : DeleteWalletUseCase {
    override suspend fun invoke(id : String) : Boolean{
        deleteUserWallet(id)
      return  walletGateWay.deleteWallet(id)
    }
}