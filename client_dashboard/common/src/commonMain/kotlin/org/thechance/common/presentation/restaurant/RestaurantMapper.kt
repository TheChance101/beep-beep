package org.thechance.common.presentation.restaurant

import org.thechance.common.domain.entity.AddRestaurant
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.entity.Time

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

private fun parseToCustomTime(time: String): Time {
    return try {
        Time(time.split(":")[0].toInt(), time.split(":")[1].toInt())
    } catch (e: Exception) {
        throw IllegalStateException("format must be HH:MM")
    }
}
