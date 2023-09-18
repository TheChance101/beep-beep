package data.remote.mapper

import data.remote.model.RestaurantDto
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

fun Restaurant.toDto(): RestaurantDto {
    return RestaurantDto(
        id = id,
        ownerId = ownerId,
        ownerUsername = ownerUsername,
        name = name,
        description = description,
        priceLevel = priceLevel,
        rate = rate,
        phone = phone,
        openingTime = openingTime,
        closingTime = closingTime,
        location = location.toDto(),
        address = address
    )
}