package org.thechance.common.data.remote.mapper

import org.thechance.common.data.remote.model.RestaurantDto
import org.thechance.common.domain.entity.Restaurant

fun RestaurantDto.toEntity() = Restaurant(
    id = id ?: "",
    name = name ?: "",
    ownerId = ownerId ?: "",
    phone = phone ?: "",
    rate = rate ?: 0.0,
    priceLevel = priceLevel ?: "",
    openingTime = openingTime ?: "",
    closingTime = closingTime ?: "",
)


fun List<RestaurantDto>.toEntity() = map(RestaurantDto::toEntity)
