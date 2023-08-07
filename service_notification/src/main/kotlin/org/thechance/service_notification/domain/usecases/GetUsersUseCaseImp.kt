package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.entity.User
import org.thechance.service_notification.domain.gateway.UserGateway

@Single
class GetUsersUseCaseImp(private val userGateway: UserGateway): GertUsersUseCase {
    override suspend fun invoke(): List<User> {
        return userGateway.getUsers()
    }
}