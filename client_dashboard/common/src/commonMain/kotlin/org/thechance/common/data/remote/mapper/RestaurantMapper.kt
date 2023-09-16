package org.thechance.common.data.remote.mapper

import org.thechance.common.data.remote.model.Location
import org.thechance.common.data.remote.model.RestaurantCreateDto
import org.thechance.common.data.remote.model.RestaurantDto
import org.thechance.common.domain.entity.NewRestaurantInfo
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.entity.Time
import org.thechance.common.domain.entity.Time.Companion.parseToCustomTime

fun RestaurantDto.toEntity() = Restaurant(
    id = id ?: "",
    name = name ?: "",
    ownerUsername = username ?: "",
    phoneNumber = phone ?: "",
    rating = rating ?: 0.0,
    priceLevel = priceLevel ?: 0,
    workingHours = this.openingTime?.replace(" ", "")?.split("-")?.run {
        Pair(
            parseToCustomTime(get(0)),
            parseToCustomTime(get(1))
        )
    } ?: Pair(Time(0, 0), Time(0, 0))
)


fun List<RestaurantDto>.toEntity() = map(RestaurantDto::toEntity)
fun NewRestaurantInfo.toDto(): RestaurantCreateDto {
    val result = RestaurantCreateDto(
            username = ownerUsername,
            name = name,
            openingTime = workingHours.first.toString(),
            closingTime = workingHours.second.toString(),
            phone = phoneNumber,
            location = Location(
                    latitude = location.split(",")[0].toDouble(),
                    longitude = location.split(",")[1].toDouble()
            )
    )
    println(result.toString())
    return result
}
