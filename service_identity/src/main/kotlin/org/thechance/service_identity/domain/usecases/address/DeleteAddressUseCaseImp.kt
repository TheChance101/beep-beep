package org.thechance.service_identity.domain.usecases.address

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.gateway.AddressGateway

@Single
class DeleteAddressUseCaseImp(private val addressGateway: AddressGateway) : DeleteAddressUseCase {
    override suspend fun invoke(id: String): Boolean {
        return addressGateway.deleteAddress(id)
    }

}