package org.thechance.service_identity.domain.usecases.user

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.UserGateWay
import org.thechance.service_identity.entity.User

@Single
class CreateUserUseCaseImp(
    private val userGateWay: UserGateWay,
): CreateUserUseCase {

    override suspend fun invoke(user: User): Boolean =
        userGateWay.createUser(user)

}