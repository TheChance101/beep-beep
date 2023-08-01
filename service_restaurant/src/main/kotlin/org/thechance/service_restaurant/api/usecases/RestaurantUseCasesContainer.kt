package org.thechance.service_restaurant.api.usecases

import org.koin.core.annotation.Single

@Single
data class RestaurantUseCasesContainer(
    val getRestaurants: GetRestaurantsUseCase,
    val getRestaurantDetails: GetRestaurantDetailsUseCase,
    val addRestaurant: CreateRestaurantUseCase,
    val updateRestaurant: UpdateRestaurantUseCase,
    val deleteRestaurant: DeleteRestaurantUseCase
)