package org.thechance.service_identity.domain.usecases.user

import org.thechance.service_identity.entity.User
import org.thechance.service_identity.entity.Wallet

interface CreateUserUseCase {
    suspend fun invoke(user: User): Boolean
}