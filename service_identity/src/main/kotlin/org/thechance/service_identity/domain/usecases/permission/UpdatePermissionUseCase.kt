package org.thechance.service_identity.domain.usecases.permission

import org.thechance.service_identity.domain.entity.Permission

interface UpdatePermissionUseCase {
    suspend operator fun invoke(id: String, permission: Permission): Boolean
}
