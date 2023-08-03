package org.thechance.service_identity.domain.usecases.user

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.UserGateWay

@Single
class RemovePermissionFromUserUseCaseImpl(private val userGateway: UserGateWay) :
    RemovePermissionFromUserUseCase {

    override suspend fun invoke(userId: String, permissionId: String) {
        userGateway.removePermissionFromUser(userId, permissionId)
    }

}