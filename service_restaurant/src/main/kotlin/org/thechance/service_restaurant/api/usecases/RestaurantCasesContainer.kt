package org.thechance.service_restaurant.api.usecases

import org.koin.core.annotation.Single

@Single
data class RestaurantCasesContainer(
    val getRestaurant: GetRestaurantUseCase,
    val addRestaurant: CreateRestaurantUseCase
)