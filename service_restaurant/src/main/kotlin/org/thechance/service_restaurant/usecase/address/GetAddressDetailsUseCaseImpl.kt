package org.thechance.service_restaurant.usecase.address

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.AddressGateway
import org.thechance.service_restaurant.entity.Address

@Single
class GetAddressDetailsUseCaseImpl(
    private val addressGateway: AddressGateway
): GetAddressDetailsUseCase {
    override suspend fun invoke(addressId: String): Address {
        return addressGateway.getAddressDetails(addressId) ?: throw Throwable()
    }
}