package org.thechance.common.data.mapper

import org.thechance.common.data.remote.model.RestaurantDto
import org.thechance.common.domain.entity.Restaurant
import java.text.SimpleDateFormat

fun RestaurantDto.toEntity() = Restaurant(
    id = id,
    name = name,
    ownerUsername = ownerUsername,
    phoneNumber = phoneNumber,
    rating = rating,
    priceLevel = priceLevel,
    workingHours = Pair(
        SimpleDateFormat("HH:mm").parse(workingHours.split("-")[0]),
        SimpleDateFormat("HH:mm").parse(workingHours.split("-")[1])
    )
)


fun List<RestaurantDto>.toEntity() = map(RestaurantDto::toEntity)
