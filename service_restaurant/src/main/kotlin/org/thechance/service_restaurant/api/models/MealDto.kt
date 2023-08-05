package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class MealDto(
    val id: String? = null,
    val restaurantId: String,
    val name: String,
    val description: String,
    val price: Double,
    val cuisines: List<CuisineDto> = emptyList()
)
