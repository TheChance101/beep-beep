package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class CuisineDto(
    val id: String,
    val name: String? = null
)
