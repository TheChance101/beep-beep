package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.RestaurantDetailsDto
import org.thechance.service_restaurant.api.models.RestaurantDto
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.utils.NULL_DOUBLE

fun RestaurantDto.toEntity() = Restaurant(
    id = id ?: "",
    ownerId = ownerId,
    name = name ?: "",
    description = description ?: "",
    priceLevel = priceLevel ?: "",
    rate = rate ?: NULL_DOUBLE,
    phone = phone ?: "",
    openingTime = openingTime ?: "",
    closingTime = closingTime ?: "",
    address = address.toEntity()
)

fun Restaurant.toDto() = RestaurantDto(
    id = id,
    ownerId = ownerId,
    name = name,
    description = description,
    priceLevel = priceLevel,
    rate = rate,
    phone = phone,
    openingTime = openingTime,
    closingTime = closingTime,
    address = address.toDto()
)

fun Restaurant.toDetailsDto() = RestaurantDetailsDto(
    id = id,
    name = name,
    description = description?:"",
    priceLevel = priceLevel?:"",
    rate = rate?: 0.0,
    phone = phone,
    openingTime = openingTime,
    closingTime = closingTime,
    address = address.toDto()
)

fun List<Restaurant>.toDto(): List<RestaurantDto> = map { it.toDto() }
