package org.thechance.service_restaurant.entity

import org.thechance.service_restaurant.api.models.RestaurantDto
import org.thechance.service_restaurant.data.collection.RestaurantCollection

data class Restaurant(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val priceLevel: String? = null,
    val rate: Double? = null,
    val phone: String? = null,
    val openingTime: String? = null,
    val closingTime: String? = null,
    val isDeleted: Boolean = false,
    val addresses: List<Address>? = null,
//    val meals: List<ObjectId>,
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
            closingTime = closingTime,
            addresses = addresses?.toDto()
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
            closingTime = closingTime,
        )
    }
}

fun List<Restaurant>.toDto(): List<RestaurantDto> = map { it.toDto() }