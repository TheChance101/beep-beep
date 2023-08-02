package org.thechance.service_identity.domain.usecases.user

import org.thechance.service_identity.domain.entity.User

interface UpdateUserUseCase {
    suspend fun invoke(id: String, user: User): Boolean
}