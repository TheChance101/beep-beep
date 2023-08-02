package org.thechance.service_identity.domain.usecases.permission

import org.thechance.service_identity.domain.entity.Permission

interface CreatePermissionUseCase {
    suspend operator fun invoke(permission: Permission): Boolean
}


