package org.thechance.common.data.remote.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class RestaurantResponse(
    @SerializedName("items")
    val restaurants: List<RestaurantDto>,
    @SerializedName("total")
    val total: Int,
)

@Serializable
data class RestaurantDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("ownerId")
    val ownerId: String? = null,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("rate")
    val rate: Double? = null,
    @SerialName("price_level")
    val priceLevel: String? = null,
    @SerialName("openingTime")
    val openingTime: String? = null,
    @SerialName("closingTime")
    val closingTime: String? = null,
    @SerialName("address")
    val address: String? = null,
    @SerialName("location")
    val location: LocationDto? = null,
)

@Serializable
data class LocationDto(
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lng")
    val longitude: Double,
)


