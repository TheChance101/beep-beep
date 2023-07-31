package org.thechance.service_identity.domain.usecase.address


import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.gateway.AddressGateway

@Single
class UpdateAddressUseCaseImp(private val addressGateway: AddressGateway) : UpdateAddressUseCase {
    override suspend fun invoke(id: String, address: Address): Boolean {
        return addressGateway.updateAddress(id, address)
    }

}