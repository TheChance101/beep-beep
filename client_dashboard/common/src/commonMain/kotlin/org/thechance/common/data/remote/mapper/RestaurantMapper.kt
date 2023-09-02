package org.thechance.common.data.remote.mapper

import org.thechance.common.data.remote.model.RestaurantDto
import org.thechance.common.domain.entity.Restaurant
import java.text.SimpleDateFormat

fun RestaurantDto.toEntity() = Restaurant(
    id = id ?: "",
    name = name ?: "",
    ownerUsername = ownerUsername ?: "",
    phoneNumber = phoneNumber ?: "",
    rating = rating ?: 0.0,
    priceLevel = priceLevel ?: 0,
    workingHours = Pair(
        SimpleDateFormat("HH:mm").parse(workingHours?.split("-")?.get(0)),
        SimpleDateFormat("HH:mm").parse(workingHours?.split("-")?.get(1))
    )
)


fun List<RestaurantDto>.toEntity() = map(RestaurantDto::toEntity)
