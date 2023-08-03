package org.thechance.service_identity.domain.usecases.wallet

import org.thechance.service_identity.domain.entity.Wallet


interface AddWalletUseCase {
    suspend operator fun invoke(wallet: Wallet): Boolean
}