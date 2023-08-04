package org.thechance.service_identity.domain.usecases.permission

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.gateway.DataBaseGateway

@Single
class GetPermissionUseCaseImp(
    private val permissionGateway: DataBaseGateway
) :
    GetPermissionUseCase {
    override suspend fun invoke(permissionId: String): Permission {
        return permissionGateway.getPermission(permissionId) ?: throw Throwable("Invalid id")
    }
}