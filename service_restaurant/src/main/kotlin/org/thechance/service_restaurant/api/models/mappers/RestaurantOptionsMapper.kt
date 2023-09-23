package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.RestaurantOptionsDto
import org.thechance.service_restaurant.domain.entity.RestaurantOptions

fun RestaurantOptionsDto.toEntity() = RestaurantOptions(
    page = page ?: 10,
    limit = limit ?: 10,
    query = query,
    rating = rating,
    priceLevel = priceLevel
)