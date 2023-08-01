package org.thechance.service_identity.domain.usecases.user_details

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.UserDetailsGateway

@Single
class RemoveAddressFromUserUseCaseImpl(private val userDetailsGateway: UserDetailsGateway) :
    RemoveAddressFromUserUseCase {

    override suspend fun invoke(userId: String, addressId: String) {
        userDetailsGateway.removeAddressFromUser(userId, addressId)
    }

}