package org.thechance.service_identity.domain.usecases.permission

import org.koin.core.annotation.Single

@Single
data class PermissionUseCasesContainer(
    val addPermission: CreatePermissionUseCase,
    val updatePermission: UpdatePermissionUseCase,
    val deletePermission: DeletePermissionUseCase,
    val getPermission:GetPermissionUseCase
)
