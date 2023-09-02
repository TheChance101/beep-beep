package org.thechance.common.data.remote.mapper

import org.thechance.common.data.remote.model.RestaurantDto
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.entity.Time
import org.thechance.common.domain.entity.Time.Companion.parseToCustomTime

fun RestaurantDto.toEntity() = Restaurant(
    id = id ?: "",
    name = name ?: "",
    ownerUsername = ownerUsername ?: "",
    phoneNumber = phoneNumber ?: "",
    rating = rating ?: 0.0,
    priceLevel = priceLevel ?: 0,
    workingHours = workingHours?.replace(" ", "")?.split("-")?.run {
        Pair(
            parseToCustomTime(get(0)),
            parseToCustomTime(get(1))
        )
    } ?: Pair(Time(0, 0), Time(0, 0))
)


fun List<RestaurantDto>.toEntity() = map(RestaurantDto::toEntity)
