package org.thechance.common.presentation.restaurant

import org.thechance.common.domain.entity.Restaurant

fun Restaurant.toUiState(): RestaurantUiState.RestaurantDetailsUiState =
    RestaurantUiState.RestaurantDetailsUiState(
        name = name,
        ownerUsername = ownerUsername,
        phoneNumber = phoneNumber,
        rating = rating,
        priceLevel = priceLevel,
        workingHours = workingHours,
    )


fun List<Restaurant>.toUiState() = map(Restaurant::toUiState)