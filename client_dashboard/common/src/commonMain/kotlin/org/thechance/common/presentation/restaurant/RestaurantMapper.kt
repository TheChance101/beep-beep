package org.thechance.common.presentation.restaurant

import org.thechance.common.domain.entity.NewRestaurantInfo
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.entity.Time
import org.thechance.common.domain.entity.Time.Companion.parseToCustomTime

fun Restaurant.toUiState(): RestaurantUiState.RestaurantDetailsUiState =
    RestaurantUiState.RestaurantDetailsUiState(
        name = name,
        ownerUsername = ownerUsername,
        phoneNumber = phoneNumber,
        rating = rating,
        priceLevel = priceLevel,
        workingHours = workingHours.toWorkingHoursString()
    )

private fun Pair<Time, Time>.toWorkingHoursString() = "$first - $second"

fun List<Restaurant>.toUiState() = map(Restaurant::toUiState)

fun NewRestaurantInfoUiState.toEntity() = NewRestaurantInfo(
    name = name,
    ownerUsername = ownerUsername,
    phoneNumber = phoneNumber,
    location = location,
    workingHours = Pair(
        parseToCustomTime(startTime),
        parseToCustomTime(endTime)
    ),
)