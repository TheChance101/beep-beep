package org.thechance.service_identity.domain.usecases.user

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.gateway.UserGateWay

@Single
class UpdateUserUseCaseImp(
    private val gateWay: UserGateWay,
): UpdateUserUseCase {
    override suspend operator fun invoke(id: String, user: User): Boolean =
        gateWay.updateUser(id, user)


}