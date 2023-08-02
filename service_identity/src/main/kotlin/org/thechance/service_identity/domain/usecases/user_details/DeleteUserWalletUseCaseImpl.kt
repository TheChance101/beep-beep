package org.thechance.service_identity.domain.usecases.user_details

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.UserDetailsGateway
import org.thechance.service_identity.domain.usecases.wallet.DeleteWalletUseCase

@Single
class DeleteUserWalletUseCaseImpl(
    private val userDetailsGateway: UserDetailsGateway,
    private val deleteWallet: DeleteWalletUseCase
) : DeleteUserWalletUseCase {

    override suspend fun invoke(walletId: String) {
        deleteWallet(walletId)
        userDetailsGateway.deleteUserWallet(walletId)
    }

}