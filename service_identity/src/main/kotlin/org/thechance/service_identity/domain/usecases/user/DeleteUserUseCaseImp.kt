package org.thechance.service_identity.domain.usecases.user

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.UserGateWay

@Single
class DeleteUserUseCaseImp(
    private val gateWay: UserGateWay
): DeleteUserUseCase {
    override suspend operator fun invoke(id: String): Boolean =
        gateWay.deleteUser(id)

}