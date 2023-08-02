package org.thechance.service_restaurant.usecase.address

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.UpdateAddressUseCase
import org.thechance.service_restaurant.entity.Address
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class UpdateAddressUseCaseImpl(
    private val restaurantGateway: RestaurantGateway
) : UpdateAddressUseCase {
    override suspend fun invoke(address: Address): Boolean {
        return restaurantGateway.updateAddress(address)
    }
}