package org.thechance.service_restaurant.usecase.restaurant

import org.thechance.service_restaurant.entity.Restaurant

interface GetRestaurantsUseCase {
    suspend operator fun invoke(page: Int, limit: Int): List<Restaurant>
}

interface GetRestaurantDetailsUseCase {
    suspend operator fun invoke(restaurantId: String): Restaurant
}

interface CreateRestaurantUseCase {
    suspend operator fun invoke(restaurant: Restaurant): Boolean
}

interface UpdateRestaurantUseCase {
    suspend operator fun invoke(restaurant: Restaurant): Boolean
}

interface DeleteRestaurantUseCase {
    suspend operator fun invoke(restaurantId: String): Boolean
}
