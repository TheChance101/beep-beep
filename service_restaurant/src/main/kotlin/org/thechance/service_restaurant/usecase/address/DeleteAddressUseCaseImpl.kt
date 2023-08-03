package org.thechance.service_restaurant.usecase.address

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.AddressGateway

@Single
class DeleteAddressUseCaseImpl(
    private val addressGateway: AddressGateway
) : DeleteAddressUseCase {
    override suspend fun invoke(addressId: String): Boolean {
        return addressGateway.deleteAddress(addressId)
    }
}