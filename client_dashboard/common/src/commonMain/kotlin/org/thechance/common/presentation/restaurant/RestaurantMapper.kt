package org.thechance.common.presentation.restaurant

import org.thechance.common.domain.entity.Restaurant

fun Restaurant.toUiState(): RestaurantUiState.RestaurantDetailsUiState =
    RestaurantUiState.RestaurantDetailsUiState(
        restaurantName = restaurantName,
        ownerUsername = ownerUsername,
        phoneNumber = phoneNumber,
        rating = rating,
        priceLevel = priceLevel,
        workingHours = workingHours,
    )


fun List<Restaurant>.toUiState() = map { it.toUiState() }