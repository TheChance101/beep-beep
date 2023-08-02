package org.thechance.service_restaurant.usecase.address

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.GetAddressesUseCase
import org.thechance.service_restaurant.entity.Address
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class GetAddressesUseCaseImpl(
    private val restaurantGateway: RestaurantGateway
): GetAddressesUseCase {
    override suspend fun invoke(): List<Address> {
        return restaurantGateway.getAddresses()
    }
}