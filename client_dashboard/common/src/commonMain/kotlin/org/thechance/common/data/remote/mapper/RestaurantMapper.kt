package org.thechance.common.data.remote.mapper

import org.thechance.common.data.remote.model.Location
import org.thechance.common.data.remote.model.RestaurantCreateDto
import org.thechance.common.data.remote.model.RestaurantDto
import org.thechance.common.domain.entity.NewRestaurantInfo
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

fun NewRestaurantInfo.toDto(): RestaurantCreateDto {
    return RestaurantCreateDto(
            name = name,
            username = ownerUsername,
            openingTime = openingTime,
            closingTime = closingTime,
            phone = phoneNumber,
            location = Location(
                    latitude = location.split(",")[0].toDouble(),
                    longitude = location.split(",")[1].toDouble()
            )
    )
}
