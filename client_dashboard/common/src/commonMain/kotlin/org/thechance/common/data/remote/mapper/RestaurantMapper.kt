package org.thechance.common.data.remote.mapper

import org.thechance.common.data.remote.model.RestaurantDto
import org.thechance.common.domain.entity.Restaurant

fun RestaurantDto.toEntity() = Restaurant(
    id = id,
    restaurantName = restaurantName,
    ownerUsername = ownerUsername,
    phoneNumber = phoneNumber,
    rating = rating,
    priceLevel = priceLevel,
    workingHours = workingHours
)


fun List<RestaurantDto>.toEntity() = map { it.toEntity() }
