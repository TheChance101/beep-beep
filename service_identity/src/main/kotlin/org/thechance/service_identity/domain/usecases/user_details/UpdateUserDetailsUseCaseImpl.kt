package org.thechance.service_identity.domain.usecases.user_details

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.UserDetails
import org.thechance.service_identity.domain.gateway.UserDetailsGateway

@Single
class UpdateUserDetailsUseCaseImpl(private val userDetailsGateway: UserDetailsGateway) : UpdateUserDetailsUseCase {

    override suspend fun invoke(userDetails: UserDetails) {
        userDetailsGateway.updateUserDetails(userDetails)
    }

}