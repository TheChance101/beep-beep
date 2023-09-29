package org.thechance.common.data.gateway.remote.mapper

import org.thechance.common.data.gateway.remote.model.LocationDto
import org.thechance.common.data.gateway.remote.model.RestaurantCreateDto
import org.thechance.common.data.gateway.remote.model.RestaurantDto
import org.thechance.common.domain.entity.Location
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.entity.RestaurantInformation

fun RestaurantDto.toEntity() = Restaurant(
    id = id ?: "",
    name = name ?: "",
    ownerId = ownerId ?: "",
    ownerUsername = ownerUserName ?: "",
    phone = phone ?: "",
    rate = rate ?: 0.0,
    priceLevel = priceLevel ?: "",
    openingTime = openingTime ?: "",
    closingTime = closingTime ?: "",
    location = location?.toEntity() ?: Location(0.0, 0.0)
)

fun LocationDto.toEntity() = Location(
    latitude = latitude,
    longitude = longitude
)

fun List<RestaurantDto>.toEntity() = map(RestaurantDto::toEntity)

fun RestaurantInformation.toDto(): RestaurantCreateDto {
    return RestaurantCreateDto(
        name = name,
        ownerUserName = ownerUsername,
        openingTime = openingTime,
        closingTime = closingTime,
        phone = phoneNumber,
        location = LocationDto(
            latitude = location.split(",")[0].toDouble(),
            longitude = location.split(",")[1].toDouble()
        )
    )
}

fun getPriceLevelOrNull(priceLevel: Int): String? = if (priceLevel > 0) "$".repeat(priceLevel) else null

fun getRatingOrNull(rating: Double): Double? = if (rating > 0.0) rating else null


