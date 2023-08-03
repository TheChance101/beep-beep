package org.thechance.service_identity.domain.usecases.address


import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.gateway.AddressGateway

@Single
class GetAddressUseCaseImp(private val addressGateway: AddressGateway) : GetAddressUseCase {
    override suspend fun invoke(id: String): Address {
        return addressGateway.getAddress(id) ?: throw Throwable("Invalid id")
    }
}