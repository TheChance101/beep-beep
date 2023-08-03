package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.RestaurantDto
import org.thechance.service_restaurant.entity.Restaurant

fun RestaurantDto.toEntity() = Restaurant(
    id = id,
    name = name,
    description = description,
    priceLevel = priceLevel,
    rate = rate,
    phone = phone,
    openingTime = openingTime,
    closingTime = closingTime
)

fun Restaurant.toDto()= RestaurantDto(
        id = id ?: "",
        name = name,
        description = description,
        priceLevel = priceLevel,
        rate = rate,
        phone = phone,
        openingTime = openingTime,
        closingTime = closingTime
    )

fun List<Restaurant>.toDto(): List<RestaurantDto> = map { it.toDto() }
