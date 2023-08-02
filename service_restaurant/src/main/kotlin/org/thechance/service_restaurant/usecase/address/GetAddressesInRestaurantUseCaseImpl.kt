package org.thechance.service_restaurant.usecase.address

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.api.usecases.GetAddressesInRestaurantUseCase
import org.thechance.service_restaurant.entity.Address
import org.thechance.service_restaurant.usecase.gateway.RestaurantGateway

@Single
class GetAddressesInRestaurantUseCaseImpl(
    private val restaurantGateway: RestaurantGateway
) : GetAddressesInRestaurantUseCase {
    override suspend fun invoke(restaurantId: String): List<Address> {
        return restaurantGateway.getAddressesInRestaurant(restaurantId)
    }
}