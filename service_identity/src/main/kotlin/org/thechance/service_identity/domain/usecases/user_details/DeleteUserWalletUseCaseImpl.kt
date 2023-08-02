package org.thechance.service_identity.domain.usecases.user_details

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.UserDetailsGateway

@Single
class DeleteUserWalletUseCaseImpl(
    private val userDetailsGateway: UserDetailsGateway,
    private val deleteUserWallet: DeleteUserWalletUseCase
) : DeleteUserWalletUseCase {

    override suspend fun invoke(walletId: String) {
        deleteUserWallet(walletId)
        userDetailsGateway.deleteUserWallet(walletId)
    }

}