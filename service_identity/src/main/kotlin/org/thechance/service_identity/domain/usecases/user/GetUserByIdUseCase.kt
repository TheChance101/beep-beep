package org.thechance.service_identity.domain.usecases.user

import org.thechance.service_identity.domain.entity.User

interface GetUserByIdUseCase {
    suspend operator fun invoke(id: String): User
}