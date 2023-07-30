package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.RestaurantDto
import org.thechance.service_restaurant.entity.Restaurant

fun Restaurant.toDto(): RestaurantDto {
    return RestaurantDto(
        name = name,
        id = id.toString()
    )
}

fun List<Restaurant>.toDto(): List<RestaurantDto> = map { it.toDto() }