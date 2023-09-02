package org.thechance.api_gateway.data.model.restaurant

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.thechance.api_gateway.data.model.LocationDto

@Serializable
data class RestaurantDto(
    @SerialName("id") val id: String? = null,
    @SerialName("ownerId") val ownerId: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("priceLevel") val priceLevel: String? = null,
    @SerialName("rate") val rate: Double? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("openingTime") val openingTime: String? = null,
    @SerialName("closingTime") val closingTime: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("location") val locationDto: LocationDto? = null,
)