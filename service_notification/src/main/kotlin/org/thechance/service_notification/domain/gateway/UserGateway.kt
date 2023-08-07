package org.thechance.service_notification.domain.gateway

import org.thechance.service_notification.domain.entity.User

interface UserGateway {
    suspend fun createUser(user: User)
}