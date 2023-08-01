package org.thechance.service_identity.domain.usecases.wallet

import org.thechance.service_identity.entity.Wallet


interface AddWalletUseCase {
    suspend fun invoke(wallet: Wallet): Boolean
}