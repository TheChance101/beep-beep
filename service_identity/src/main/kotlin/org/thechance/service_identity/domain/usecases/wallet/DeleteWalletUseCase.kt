package org.thechance.service_identity.domain.usecases.wallet

interface DeleteWalletUseCase {
    suspend operator fun invoke(id: String): Boolean
}