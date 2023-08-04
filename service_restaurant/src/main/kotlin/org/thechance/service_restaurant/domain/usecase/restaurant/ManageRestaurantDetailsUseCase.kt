package org.thechance.service_restaurant.domain.usecase.restaurant

import org.thechance.service_restaurant.domain.entity.Address
import org.thechance.service_restaurant.domain.entity.Restaurant

interface ManageRestaurantDetailsUseCase {
    suspend fun getRestaurantDetails(restaurantId: String): Restaurant
    suspend fun addAddressToRestaurant(restaurantId: String, address: Address)
    suspend fun updateAddressToRestaurant(restaurantId: String, address: Address)
    suspend fun deleteAddressToRestaurant(restaurantId: String, addressIds: List<String>)

}

