package org.thechance.service_identity.domain.usecases.user

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.gateway.UserGateWay

@Single
class GetUsersUseCaseImp(
    private val gateWay: UserGateWay
): GetUsersUseCase {
    override suspend operator fun invoke(): List<User> =
        gateWay.getUsers()



}