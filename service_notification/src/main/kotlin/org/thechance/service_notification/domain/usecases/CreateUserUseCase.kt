package org.thechance.service_notification.domain.usecases

import org.thechance.service_notification.domain.entity.User

interface CreateUserUseCase {
    suspend operator fun invoke(user:User)
}