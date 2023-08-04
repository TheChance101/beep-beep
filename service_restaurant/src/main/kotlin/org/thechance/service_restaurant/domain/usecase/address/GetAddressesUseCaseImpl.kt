package org.thechance.service_restaurant.domain.usecase.address

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.gateway.AddressGateway
import org.thechance.service_restaurant.domain.entity.Address

@Single
class GetAddressesUseCaseImpl(
    private val addressGateway: AddressGateway
): GetAddressesUseCase {
    override suspend fun invoke(): List<Address> {
        return addressGateway.getAddresses()
    }
}