package org.thechance.api_gateway.data.model.restaurant

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CuisineDto(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String
)