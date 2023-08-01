package org.thechance.service_identity.domain.usecases.user_details

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.UserDetailsGateway

@Single
class AddAddressToUserUseCaseImpl(private val userDetailsGateway: UserDetailsGateway) : AddAddressToUserUseCase {

    override suspend fun invoke(userId: String, addressId: String) {
        userDetailsGateway.addAddressToUser(userId, addressId)
    }

}