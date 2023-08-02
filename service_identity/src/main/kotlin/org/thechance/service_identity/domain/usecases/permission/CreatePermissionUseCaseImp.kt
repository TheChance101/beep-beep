package org.thechance.service_identity.domain.usecases.permission

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.gateway.PermissionGateway

@Single
class CreatePermissionUseCaseImp(private val permissionGateway: PermissionGateway) :
    CreatePermissionUseCase {
    override suspend fun invoke(permission: Permission): Boolean {
        checkValidation(permission)
        return permissionGateway.addPermission(permission)
    }

    private fun checkValidation(permission: Permission) {

    }
}