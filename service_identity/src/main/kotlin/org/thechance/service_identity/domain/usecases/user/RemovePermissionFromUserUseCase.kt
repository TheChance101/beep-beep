package org.thechance.service_identity.domain.usecases.user

interface RemovePermissionFromUserUseCase {

    suspend operator fun invoke(userId: String, permissionId: String)

}