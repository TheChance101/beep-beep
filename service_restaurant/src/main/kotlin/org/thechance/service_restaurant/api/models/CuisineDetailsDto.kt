package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable


@Serializable
data class CuisineDetailsDto(
    val id: String? = null,
    val name: String? = null,
    val image: String? = null,
    val meals: List<MealDto> = emptyList()
)
