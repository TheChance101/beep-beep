package org.thechance.service_identity.domain.usecases.permission

import org.thechance.service_identity.domain.entity.Permission

interface GetPermissionUseCase {
    suspend operator fun invoke(id : String) : Permission

}