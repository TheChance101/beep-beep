package org.thechance.service_restaurant.domain.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Address
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.RestaurantGateway

@Single
class ManageRestaurantDetailsUseCaseImp(private val restaurantGateway: RestaurantGateway):ManageRestaurantDetailsUseCase {
    override suspend fun getRestaurantDetails(restaurantId: String): Restaurant {
        return if (restaurantId.isNotEmpty()) {
            restaurantGateway.getRestaurant(restaurantId) ?: throw Throwable()
        } else {
            throw Throwable()
        }
    }

    override suspend fun addAddressToRestaurant(restaurantId: String, address: Address) {
        TODO("Not yet implemented")
    }

    override suspend fun updateAddressToRestaurant(restaurantId: String, address: Address) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAddressToRestaurant(restaurantId: String, addressIds: List<String>) {
        TODO("Not yet implemented")
    }
}