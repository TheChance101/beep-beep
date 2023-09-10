package org.thechance.common.presentation.restaurant

import org.thechance.common.data.remote.model.Location
import org.thechance.common.data.remote.model.RestaurantCreateDto
import org.thechance.common.domain.entity.NewRestaurantInfo
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.entity.Time
import org.thechance.common.domain.entity.Time.Companion.parseToCustomTime

fun Restaurant.toUiState(): RestaurantUiState.RestaurantDetailsUiState =
    RestaurantUiState.RestaurantDetailsUiState(
        id = id,
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

fun NewRestaurantInfo.toDto(): RestaurantCreateDto{
    val result = RestaurantCreateDto(
        ownerId = "64f363af5ddbc15bfd1efcf4",
        name = name,
        openingTime = workingHours.first.toString(),
        closingTime = workingHours.second.toString(),
        phone = phoneNumber,
        location = Location(
            latitude = location.split(",")[0].toDouble(),
            longitude = location.split(",")[1].toDouble()
        )
    )
    println(result.toString())
    return result
}
