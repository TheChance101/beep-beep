package org.thechance.service_identity.domain.usecase.address

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.gateway.AddressGateway

@Single
class CreateAddressUseCaseImp(private val addressGateway: AddressGateway) : CreateAddressUseCase {
    override suspend fun invoke(address: Address): Boolean {
        return addressGateway.addAddress(address)
    }

}