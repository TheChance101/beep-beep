package org.thechance.service_identity.domain.usecases.user

import org.thechance.service_identity.domain.entity.User

interface GetUsersUseCase {
    suspend fun invoke(): List<User>
}