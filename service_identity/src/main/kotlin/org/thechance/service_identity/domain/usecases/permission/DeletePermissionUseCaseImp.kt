package org.thechance.service_identity.domain.usecases.permission

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.DataBaseGateway

@Single
class DeletePermissionUseCaseImp(private val permissionGateway: DataBaseGateway) :
    DeletePermissionUseCase {
    override suspend fun invoke(permissionId: String): Boolean {
        return if (permissionId.isNotEmpty()) {
            permissionGateway.deletePermission(permissionId)
        } else {
            throw Throwable()
        }
    }
}