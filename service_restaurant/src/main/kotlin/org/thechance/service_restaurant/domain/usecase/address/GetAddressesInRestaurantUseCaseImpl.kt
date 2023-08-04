package org.thechance.service_restaurant.domain.usecase.address

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.gateway.RestaurantGateway
import org.thechance.service_restaurant.domain.entity.Address

@Single
class GetAddressesInRestaurantUseCaseImpl(
    private val restaurantGateway: RestaurantGateway
) : GetAddressesInRestaurantUseCase {
    override suspend fun invoke(restaurantId: String): List<Address> {
        return restaurantGateway.getAddressesInRestaurant(restaurantId)
    }
}