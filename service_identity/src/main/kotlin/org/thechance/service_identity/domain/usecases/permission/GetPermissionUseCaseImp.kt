package org.thechance.service_identity.domain.usecases.permission

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.gateway.PermissionGateway

@Single
class GetPermissionUseCaseImp(
    private val permissionGateway: PermissionGateway
) :
    GetPermissionUseCase {
    override suspend fun invoke(permissionId: String): Permission {
        return permissionGateway.getPermission(permissionId) ?: throw Throwable("Invalid id")
    }
}