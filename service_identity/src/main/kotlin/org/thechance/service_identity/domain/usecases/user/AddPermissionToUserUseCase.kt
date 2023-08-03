package org.thechance.service_identity.domain.usecases.user

interface AddPermissionToUserUseCase {

    suspend operator fun invoke(userId: String, permissionId: String)

}