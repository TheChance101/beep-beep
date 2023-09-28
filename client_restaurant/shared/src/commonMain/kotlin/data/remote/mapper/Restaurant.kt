package data.remote.mapper

import data.remote.model.RestaurantDto
import data.remote.model.UpdateRestaurantDto
import domain.entity.Restaurant

fun List<RestaurantDto>.toEntity():List<Restaurant> = map { it.toEntity() }

fun RestaurantDto.toEntity(): Restaurant {
    return Restaurant(
        id = id,
        ownerId = ownerId.toString(),
        ownerUsername = ownerUsername ?: "",
        name = name ?: "",
        description = description ?: "",
        priceLevel = priceLevel ?: "",
        rate = rate ?: 0.0,
        phone = phone ?: "",
        openingTime = openingTime ?: "",
        closingTime = closingTime ?: "",
        location = location.toEntity(),
        address = address ?: ""
    )
}

fun Restaurant.toDto(): UpdateRestaurantDto {
    return UpdateRestaurantDto(
        id = id,
        name = name,
        description = description,
        priceLevel = priceLevel,
        rate = rate,
        phone = phone,
        openingTime = openingTime,
        closingTime = closingTime,
        address = address
    )
}