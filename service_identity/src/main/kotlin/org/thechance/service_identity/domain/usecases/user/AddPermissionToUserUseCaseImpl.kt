package org.thechance.service_identity.domain.usecases.user

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.UserGateWay

@Single
class AddPermissionToUserUseCaseImpl(private val userGateway: UserGateWay) : AddPermissionToUserUseCase {

    override suspend fun invoke(userId: String, permissionId: String) {
        userGateway.addPermissionToUser(userId, permissionId)
    }

}