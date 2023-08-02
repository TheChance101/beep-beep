package org.thechance.service_restaurant.usecase.address

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.DeleteAddressUseCase
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class DeleteAddressUseCaseImpl(
    private val restaurantGateway: RestaurantGateway
) : DeleteAddressUseCase {
    override suspend fun invoke(addressId: String): Boolean {
        return restaurantGateway.deleteAddress(addressId)
    }
}