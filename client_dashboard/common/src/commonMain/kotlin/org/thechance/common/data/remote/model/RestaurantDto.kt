package org.thechance.common.data.remote.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("owner_username")
    val ownerUsername: String? = null,
    @SerializedName("phone_number")
    val phoneNumber: String? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("price_level")
    val priceLevel: Int? = null,
    @SerializedName("working_hours")
    val workingHours: String? = null,
)
