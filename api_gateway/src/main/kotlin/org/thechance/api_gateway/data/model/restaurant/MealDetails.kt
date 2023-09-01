package org.thechance.api_gateway.data.model.restaurant

import kotlinx.serialization.Serializable
import org.thechance.api_gateway.data.model.Cuisine


@Serializable
data class MealDetails(
    val id: String? = null,
    val restaurantId: String? = null,
    val name: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val currency: String? = null,
    val cuisines: List<Cuisine>? = null
)