package org.thechance.service_identity.domain.usecases.permission

interface DeletePermissionUseCase {
    suspend operator fun invoke(permissionId: String): Boolean
}