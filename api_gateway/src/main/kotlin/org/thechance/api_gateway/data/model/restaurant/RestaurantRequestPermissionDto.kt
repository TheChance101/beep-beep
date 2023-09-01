package org.thechance.api_gateway.data.model.restaurant

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantRequestPermissionDto(
    @SerialName("id") val id: String? = null,
    @SerialName("restaurantName") val restaurantName: String,
    @SerialName("ownerEmail") val ownerEmail: String,
    @SerialName("cause") val cause: String
)