package org.thechance.service_notification.domain.usecases

import org.thechance.service_notification.domain.entity.User

interface GertUsersUseCase {
    suspend operator fun invoke(): List<User>
}