package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.RestaurantDetailsDto
import org.thechance.service_restaurant.api.models.RestaurantDto
import org.thechance.service_restaurant.domain.entity.Restaurant

fun RestaurantDto.toEntity() = Restaurant(
    id = id ?: "",
    ownerId = ownerId,
    name = name ?: "",
    description = description ?: "",
    priceLevel = priceLevel ?: "",
    rate = rate ?: -1.0,
    phone = phone ?: "",
    openingTime = openingTime ?: "",
    closingTime = closingTime ?: ""
)

fun Restaurant.toDto() = RestaurantDto(
    id = id,
    ownerId = ownerId,
    name = name,
    description = description,
    priceLevel = priceLevel,
    rate = rate,
    phone = phone,
    openingTime = openingTime,
    closingTime = closingTime
)

fun Restaurant.toDetailsDto() = RestaurantDetailsDto(
    id = id,
    name = name,
    description = description,
    priceLevel = priceLevel,
    rate = rate,
    phone = phone,
    openingTime = openingTime,
    closingTime = closingTime,
    addresses = addresses.toDto()
)

fun List<Restaurant>.toDto(): List<RestaurantDto> = map { it.toDto() }
