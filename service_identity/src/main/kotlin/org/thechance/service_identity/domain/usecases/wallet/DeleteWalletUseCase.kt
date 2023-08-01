package org.thechance.service_identity.domain.usecases.wallet

interface DeleteWalletUseCase {
    suspend fun invoke(id: String): Boolean
}