package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.entity.User
import org.thechance.service_notification.domain.gateway.UserGateway

@Single
class CreateUserUseCaseImp(private val userGateway: UserGateway): CreateUserUseCase {

    override suspend fun invoke(user: User) {
        return userGateway.createUser(user)
    }

}