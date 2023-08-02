package org.thechance.service_restaurant.usecase.address

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.CreateAddressUseCase
import org.thechance.service_restaurant.entity.Address
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class CreateAddressUseCaseImpl(
    private val restaurantGateway: RestaurantGateway
) : CreateAddressUseCase {
    override suspend fun invoke(address: Address): Boolean {
        return restaurantGateway.addAddress(address)
    }
}