package org.thechance.service_identity.domain.usecases.user_details

interface AddAddressToUserUseCase {

    suspend operator fun invoke(userId: String, addressId: String)

}