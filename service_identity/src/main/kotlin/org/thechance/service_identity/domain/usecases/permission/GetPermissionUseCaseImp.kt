package org.thechance.service_identity.domain.usecases.permission

import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.gateway.PermissionGateway
@Single
class GetPermissionUseCaseImp(
    private val permissionGateway: PermissionGateway
) :
    GetPermissionUseCase {
    override suspend fun invoke(id: String): Permission {
        return permissionGateway.getPermission(id) ?: throw Throwable("Invalid id")
    }
}