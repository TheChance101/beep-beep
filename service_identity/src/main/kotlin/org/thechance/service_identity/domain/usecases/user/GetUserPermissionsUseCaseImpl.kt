package org.thechance.service_identity.domain.usecases.user

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.gateway.UserGateWay

@Single
class GetUserPermissionsUseCaseImpl(private val userGateway: UserGateWay) : GetUserPermissionsUseCase {

    override suspend fun invoke(userId: String): List<Permission> {
        return userGateway.getUserPermissions(userId)
    }

}