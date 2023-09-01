package org.thechance.common.presentation.restaurant

import org.thechance.common.domain.entity.AddRestaurant
import org.thechance.common.domain.entity.CustomTime.Companion.parseToCustomTime
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.entity.toWorkingHoursString

fun Restaurant.toUiState(): RestaurantUiState.RestaurantDetailsUiState =
    RestaurantUiState.RestaurantDetailsUiState(
        name = name,
        ownerUsername = ownerUsername,
        phoneNumber = phoneNumber,
        rating = rating,
        priceLevel = priceLevel,
        workingHours = workingHours.toWorkingHoursString()
    )

fun List<Restaurant>.toUiState() = map(Restaurant::toUiState)

fun AddRestaurantDialogUiState.toEntity() = AddRestaurant(
    name = name,
    ownerUsername = ownerUsername,
    phoneNumber = phoneNumber,
    location = location,
    workingHours = Pair(
        parseToCustomTime(startTime),
        parseToCustomTime(endTime)
    ),
)