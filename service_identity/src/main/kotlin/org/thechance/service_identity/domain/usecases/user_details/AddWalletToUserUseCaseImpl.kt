package org.thechance.service_identity.domain.usecases.user_details

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.UserDetailsGateway

@Single
class AddWalletToUserUseCaseImpl(private val userDetailsGateway: UserDetailsGateway) : AddWalletToUserUseCase {

    override suspend fun invoke(userId: String, walletId: String) {
        userDetailsGateway.addWalletToUser(userId, walletId)
    }

}