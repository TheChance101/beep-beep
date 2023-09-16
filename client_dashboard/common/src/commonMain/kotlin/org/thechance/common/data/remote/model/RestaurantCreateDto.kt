package org.thechance.common.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantCreateDto(
    @SerialName("username")
    val username: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("openingTime")
    val openingTime: String?,
    @SerialName("closingTime")
    val closingTime: String?,
    @SerialName("phone")
    val phone: String?,
    @SerialName("location")
    val location: Location?
)

@Serializable
data class Location(
    @SerialName("latitude")
    val latitude: Double?,
    @SerialName("longitude")
    val longitude: Double?
)