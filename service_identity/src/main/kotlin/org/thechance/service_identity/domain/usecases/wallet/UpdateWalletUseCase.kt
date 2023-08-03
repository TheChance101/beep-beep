package org.thechance.service_identity.domain.usecases.wallet

import org.thechance.service_identity.domain.entity.Wallet


interface UpdateWalletUseCase {
    suspend operator fun invoke(id: String, wallet: Wallet): Boolean
}