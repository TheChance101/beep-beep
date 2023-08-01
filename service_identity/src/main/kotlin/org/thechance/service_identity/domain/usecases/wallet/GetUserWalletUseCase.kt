package org.thechance.service_identity.domain.usecases.wallet

import org.thechance.service_identity.domain.entity.Wallet

interface GetUserWalletUseCase {
    suspend fun invoke(id: String): Wallet
}