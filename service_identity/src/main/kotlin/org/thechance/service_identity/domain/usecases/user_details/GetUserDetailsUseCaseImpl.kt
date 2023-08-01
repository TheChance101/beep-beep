package org.thechance.service_identity.domain.usecases.user_details

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.UserDetailsGateway

@Single
class GetUserDetailsUseCaseImpl(private val userDetailsGateway: UserDetailsGateway) : GetUserDetailsUseCase {

    override suspend fun invoke(userId: String) = userDetailsGateway.getUserDetails(userId)

}