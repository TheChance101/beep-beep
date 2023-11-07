package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class CuisineDto(
    val id: String? = null,
    val name: String? = null,
    val image: String? = null
)
