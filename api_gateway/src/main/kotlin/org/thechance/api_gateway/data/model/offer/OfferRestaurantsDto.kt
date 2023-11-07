package org.thechance.api_gateway.data.model.offer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.thechance.api_gateway.data.model.restaurant.RestaurantDto

@Serializable
data class OfferRestaurantsDto(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String,
    @SerialName("restaurants") val restaurants: List<RestaurantDto>,
)