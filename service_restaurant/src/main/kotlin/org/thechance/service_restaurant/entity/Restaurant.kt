package org.thechance.service_restaurant.entity

import org.thechance.service_restaurant.api.models.RestaurantDto
import org.thechance.service_restaurant.data.collection.RestaurantCollection
import java.time.LocalTime

data class Restaurant(
    val id: String?,
    val name: String,
    val description: String,
    val priceLevel: String,
    val rate: Double,
    val phone: String,
    val openingTime: String,
    val closingTime: String,

//    val address: ObjectId,
//    val meals: List<ObjectId>,
//    val categories: List<ObjectId>,
) {
    fun toDto(): RestaurantDto {
        return RestaurantDto(
            id = id ?: "",
            name = name,
            description = description,
            priceLevel = priceLevel,
            rate = rate,
            phone = phone,
            openingTime = openingTime,
            closingTime = closingTime
        )
    }

    fun toCollection(): RestaurantCollection {
        return RestaurantCollection(
            name = name,
            description = description,
            priceLevel = priceLevel,
            rate = rate,
            phone = phone,
            openingTime = openingTime,
            closingTime = closingTime
        )
    }
}

fun List<Restaurant>.toDto(): List<RestaurantDto> = map { it.toDto() }