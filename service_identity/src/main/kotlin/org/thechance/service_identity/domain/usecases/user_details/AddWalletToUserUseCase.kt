package org.thechance.service_identity.domain.usecases.user_details

interface AddWalletToUserUseCase {

    suspend operator fun invoke(userId: String, walletId: String)

}