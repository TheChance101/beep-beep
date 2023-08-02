package org.thechance.service_restaurant.usecase.address

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.GetAddressDetailsUseCase
import org.thechance.service_restaurant.entity.Address
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class GetAddressDetailsUseCaseImpl(
    private val restaurantGateway: RestaurantGateway
): GetAddressDetailsUseCase {
    override suspend fun invoke(addressId: String): Address {
        return restaurantGateway.getAddress(addressId) ?: throw Throwable()
    }
}