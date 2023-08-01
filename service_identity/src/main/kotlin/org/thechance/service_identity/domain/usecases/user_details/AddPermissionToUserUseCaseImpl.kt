package org.thechance.service_identity.domain.usecases.user_details

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.UserDetailsGateway

@Single
class AddPermissionToUserUseCaseImpl(private val userDetailsGateway: UserDetailsGateway) : AddPermissionToUserUseCase {

    override suspend fun invoke(userId: String, permissionId: String) {
        userDetailsGateway.addPermissionToUser(userId, permissionId)
    }

}