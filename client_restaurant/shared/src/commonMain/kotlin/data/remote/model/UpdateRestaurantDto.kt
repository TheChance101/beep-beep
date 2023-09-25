package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateRestaurantDto (
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("priceLevel")
    val priceLevel: String? = null,
    @SerialName("rate")
    val rate: Double? = null,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("openingTime")
    val openingTime: String? = null,
    @SerialName("closingTime")
    val closingTime: String? = null,
    @SerialName("address")
    val address: String? = null
)