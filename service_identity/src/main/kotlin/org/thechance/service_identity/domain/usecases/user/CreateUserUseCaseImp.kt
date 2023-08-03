package org.thechance.service_identity.domain.usecases.user

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.gateway.UserGateWay

@Single
class CreateUserUseCaseImp(
    private val userGateWay: UserGateWay,
): CreateUserUseCase {

    override suspend operator fun invoke(user: User): Boolean =
        userGateWay.createUser(user)

}