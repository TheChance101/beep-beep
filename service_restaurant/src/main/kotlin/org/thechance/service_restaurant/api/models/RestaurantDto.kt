package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable
import org.thechance.service_restaurant.entity.Restaurant

@Serializable
data class RestaurantDto(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val priceLevel: String? = null,
    val rate: Double? = null,
    val phone: String? = null,
    val openingTime: String? = null,
    val closingTime: String? = null,
    val addresses: List<String>? = null,
) {
    fun toEntity(): Restaurant {
        return Restaurant(
            id = id,
            name = name,
            description = description,
            priceLevel = priceLevel,
            rate = rate,
            phone = phone,
            openingTime = openingTime,
            closingTime = closingTime,
            addresses = addresses
        )
    }
}

