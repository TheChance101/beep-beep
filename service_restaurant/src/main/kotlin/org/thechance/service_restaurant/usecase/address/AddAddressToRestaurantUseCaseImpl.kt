package org.thechance.service_restaurant.usecase.address

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway

@Single
class AddAddressToRestaurantUseCaseImpl(
    private val restaurantGateway: RestaurantGateway
) : AddAddressToRestaurantUseCase {
    override suspend fun invoke(restaurantId: String, addressesIds: List<String>): Boolean {
        return restaurantGateway.addAddressesToRestaurant(restaurantId, addressesIds)
    }
}