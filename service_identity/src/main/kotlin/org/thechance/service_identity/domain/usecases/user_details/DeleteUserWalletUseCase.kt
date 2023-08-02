package org.thechance.service_identity.domain.usecases.user_details

interface DeleteUserWalletUseCase {

    suspend operator fun invoke(walletId: String)

}