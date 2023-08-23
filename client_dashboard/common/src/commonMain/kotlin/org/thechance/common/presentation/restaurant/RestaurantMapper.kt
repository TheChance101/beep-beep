package org.thechance.common.presentation.restaurant

import org.thechance.common.domain.entity.Restaurant
import java.text.SimpleDateFormat
import java.util.Date

fun Restaurant.toUiState(): RestaurantUiState.RestaurantDetailsUiState =
    RestaurantUiState.RestaurantDetailsUiState(
        name = name,
        ownerUsername = ownerUsername,
        phoneNumber = phoneNumber,
        rating = rating,
        priceLevel = priceLevel,
        workingHours = getWorkingHours(workingHours)
    )

fun List<Restaurant>.toUiState() = map(Restaurant::toUiState)

private fun getWorkingHours(workingHours: Pair<Date, Date>): String {
    val firstDate = SimpleDateFormat("HH:mm").format(workingHours.first)
    val secondDate = SimpleDateFormat("HH:mm").format(workingHours.second)
    return "$firstDate - $secondDate"
}
