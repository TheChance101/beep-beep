package org.thechance.common.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("owner_username")
    val ownerUsername: String? = null,
    @SerialName("phone_number")
    val phoneNumber: String? = null,
    @SerialName("rating")
    val rating: Double? = null,
    @SerialName("price_level")
    val priceLevel: Int? = null,
    @SerialName("working_hours")
    val workingHours: String? = null,
)
