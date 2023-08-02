package org.thechance.service_identity.domain.usecases.user

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.gateway.UserGateWay

@Single
class GetUserByIdUseCaseImp(
    private val gateWay: UserGateWay
): GetUserByIdUseCase {
    override suspend fun invoke(id: String): User =
        gateWay.getUserById(id) ?: throw Throwable("Invalid id")
}