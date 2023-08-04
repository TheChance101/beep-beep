package org.thechance.service_restaurant.domain.usecase.address

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.gateway.AddressGateway
import org.thechance.service_restaurant.domain.entity.Address

@Single
class UpdateAddressUseCaseImpl(
    private val addressGateway: AddressGateway
) : UpdateAddressUseCase {
    override suspend fun invoke(address: Address): Boolean {
        return addressGateway.updateAddress(address)
    }
}