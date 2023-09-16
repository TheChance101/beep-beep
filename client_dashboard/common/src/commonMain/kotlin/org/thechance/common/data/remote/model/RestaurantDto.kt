package org.thechance.common.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantDto(
    @SerialName("id")
    val id: String?,
    @SerialName("rate")
    val rating: Double?,
    @SerialName("priceLevel")
    val priceLevel: Int?,
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
    val location: Location?=null,
)
