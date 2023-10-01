package org.thechance.api_gateway.data.model.restaurant

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderedMealDto(
    @SerialName("mealId") val mealId: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("quantity") val quantity: Int? = null,
    @SerialName("price") val price: Double? = null
)
