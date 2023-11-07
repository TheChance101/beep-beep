package org.thechance.api_gateway.data.model.restaurant

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CuisineDetailsDto(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String?=null,
    @SerialName("image") val image: String? = null,
    @SerialName("meals") val meals: List<MealDto>? = null
)