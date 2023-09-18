package org.thechance.common.presentation.restaurant

import org.thechance.common.data.remote.model.Location
import org.thechance.common.data.remote.model.RestaurantCreateDto
import org.thechance.common.domain.entity.NewRestaurantInfo
import org.thechance.common.domain.entity.Restaurant

fun Restaurant.toUiState(): RestaurantUiState.RestaurantDetailsUiState =
    RestaurantUiState.RestaurantDetailsUiState(
        id = id,
        name = name,
        ownerUsername = ownerId,
        phone = phone,
        rate = rate,
        priceLevel = priceLevel,
        openingTime = openingTime,
        closingTime = closingTime,
    )


fun List<Restaurant>.toUiState() = map(Restaurant::toUiState)

fun NewRestaurantInfoUiState.toEntity() = NewRestaurantInfo(
    name = name,
    ownerUsername = ownerUsername,
    phoneNumber = phoneNumber,
    location = location,
    openingTime = openingTime,
    closingTime = closingTime,
)

fun NewRestaurantInfo.toDto(): RestaurantCreateDto {
    val result = RestaurantCreateDto(
        name = name,
        username = ownerUsername,
        openingTime = openingTime,
        closingTime = closingTime,
        phone = phoneNumber,
        location = Location(
            latitude = location.split(",")[0].toDouble(),
            longitude = location.split(",")[1].toDouble()
        )
    )
    println(result.toString())
    return result
}
