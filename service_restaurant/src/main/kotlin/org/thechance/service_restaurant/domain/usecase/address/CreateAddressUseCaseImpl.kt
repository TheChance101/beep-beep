package org.thechance.service_restaurant.domain.usecase.address

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.gateway.AddressGateway
import org.thechance.service_restaurant.domain.entity.Address

@Single
class CreateAddressUseCaseImpl(
    private val addressGateway: AddressGateway
) : CreateAddressUseCase {
    override suspend fun invoke(address: Address): Boolean {
        return addressGateway.createAddress(address)
    }
}