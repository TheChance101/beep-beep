package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class MealDto(
    val id: String,
    val name: String? = null,
    val description: String? = null,
    val price: Int? = null,
)
