package org.thechance.service_identity.domain.usecase.address


import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.gateway.AddressGateway

@Single
class GetUserAddressesUseCaseImp(private val addressGateway: AddressGateway) : GetUserAddressesUseCase {
    override suspend fun invoke(userId: String): List<Address> {
        return addressGateway.getUserAddresses(userId)
    }
}