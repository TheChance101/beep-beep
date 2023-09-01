package org.thechance.common.data.remote.mapper

import org.thechance.common.data.remote.model.RestaurantDto
import org.thechance.common.domain.entity.CustomTime
import org.thechance.common.domain.entity.Restaurant

fun RestaurantDto.toEntity() = Restaurant(
    id = id,
    name = name,
    ownerUsername = ownerUsername,
    phoneNumber = phoneNumber,
    rating = rating,
    priceLevel = priceLevel,
    workingHours = workingHours.split("-").run {
        Pair(
            CustomTime(
                hour = get(0).split(":")[0].toInt(),
                minute = get(0).split(":")[1].toInt()
            ),
            CustomTime(
                hour = get(1).split(":")[0].toInt(),
                minute = get(1).split(":")[1].toInt()
            )
        )
    }
)

fun List<RestaurantDto>.toEntity() = map(RestaurantDto::toEntity)
