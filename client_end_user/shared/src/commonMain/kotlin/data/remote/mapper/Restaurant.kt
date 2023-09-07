package data.remote.mapper

import data.remote.model.RestaurantDto
import domain.entity.PriceLevel
import domain.entity.Restaurant
import domain.entity.Time

fun RestaurantDto.toEntity(): Restaurant {

    val openingTimeParts = openingTime?.split(":")
    val closingTimeParts = closingTime?.split(":")


    return Restaurant(
        id = id,
        ownerId = ownerId,
        ownerUsername = ownerUsername ?: "",
        name = name ?: "",
        description = description ?: "",
        priceLevel = PriceLevel.getPriceLevel(priceLevel ?: "") ?: PriceLevel.LOW,
        rate = rate ?: 0.0,
        phone = phone ?: "",
        openingTime = Time(
            hours = openingTimeParts?.get(0)?.toInt() ?: 0,
            minutes = openingTimeParts?.get(1)?.toInt() ?: 0
        ),
        closingTime = Time(
            hours = closingTimeParts?.get(0)?.toInt() ?: 0,
            minutes = closingTimeParts?.get(1)?.toInt() ?: 0
        ),
        location = location.toEntity(),
        address = address ?: ""
    )
}
