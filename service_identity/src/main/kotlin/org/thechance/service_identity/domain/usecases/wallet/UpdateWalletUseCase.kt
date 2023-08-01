package org.thechance.service_identity.domain.usecases.wallet

import org.thechance.service_identity.entity.Wallet


interface UpdateWalletUseCase {
    suspend fun invoke(id: String, wallet: Wallet): Boolean
}