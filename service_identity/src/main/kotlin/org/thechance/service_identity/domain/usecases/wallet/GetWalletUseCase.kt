package org.thechance.service_identity.domain.usecases.wallet

import org.thechance.service_identity.domain.entity.Wallet

interface GetWalletUseCase {
    suspend operator fun invoke(id: String): Wallet
}