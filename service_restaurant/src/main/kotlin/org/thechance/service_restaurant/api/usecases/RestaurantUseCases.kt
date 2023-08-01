package org.thechance.service_restaurant.api.usecases

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.entity.Restaurant

@Single
data class RestaurantUseCasesContainer(
    val getRestaurants: GetRestaurantsUseCase,
    val getRestaurantDetails: GetRestaurantDetailsUseCase,
    val addRestaurant: CreateRestaurantUseCase,
    val updateRestaurant: UpdateRestaurantUseCase,
    val deleteRestaurant: DeleteRestaurantUseCase
)

interface GetRestaurantsUseCase {
    suspend operator fun invoke(): List<Restaurant>
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


