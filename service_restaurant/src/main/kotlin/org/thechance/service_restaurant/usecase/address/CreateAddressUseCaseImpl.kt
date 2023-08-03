package org.thechance.service_restaurant.usecase.address

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.AddressGateway
import org.thechance.service_restaurant.entity.Address

@Single
class CreateAddressUseCaseImpl(
    private val addressGateway: AddressGateway
) : CreateAddressUseCase {
    override suspend fun invoke(address: Address): Boolean {
        return addressGateway.createAddress(address)
    }
}