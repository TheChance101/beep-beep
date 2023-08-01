package org.thechance.service_identity.domain.usecases.wallet

import org.thechance.service_identity.entity.Wallet

interface GetWalletUseCase {
    suspend fun invoke(id: String): Wallet
}