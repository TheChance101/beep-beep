package org.thechance.service_identity.domain.usecases.permission

import org.thechance.service_identity.domain.gateway.PermissionGateway

class DeletePermissionUseCaseImp(private val permissionGateway: PermissionGateway) :
    DeletePermissionUseCase {
    override suspend fun invoke(permissionId: String): Boolean {
        return if (permissionId.isNotEmpty()) {
            permissionGateway.deletePermission(permissionId)
        } else {
            throw Throwable()
        }
    }
}