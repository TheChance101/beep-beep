package org.thechance.service_identity.domain.usecases.user_details

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.UserDetailsGateway

@Single
class RemovePermissionFromUserUseCaseImpl(private val userDetailsGateway: UserDetailsGateway) :
    RemovePermissionFromUserUseCase {

    override suspend fun invoke(userId: String, permissionId: String) {
        userDetailsGateway.removePermissionFromUser(userId, permissionId)
    }

}