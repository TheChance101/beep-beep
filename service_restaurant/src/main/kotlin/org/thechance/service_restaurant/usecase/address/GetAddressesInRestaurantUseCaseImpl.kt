package org.thechance.service_restaurant.usecase.address

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.data.gateway.RestaurantGateway
import org.thechance.service_restaurant.entity.Address

@Single
class GetAddressesInRestaurantUseCaseImpl(
    private val restaurantGateway: RestaurantGateway
) : GetAddressesInRestaurantUseCase {
    override suspend fun invoke(restaurantId: String): List<Address> {
        return restaurantGateway.getAddressesInRestaurant(restaurantId)
    }
}