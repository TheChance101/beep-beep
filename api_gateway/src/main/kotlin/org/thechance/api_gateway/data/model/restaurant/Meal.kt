package org.thechance.api_gateway.data.model.restaurant

import kotlinx.serialization.Serializable

@Serializable
data class Meal(
    val id: String? = null,
    val restaurantId: String? = null,
    val name: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val currency: String? = null,
    val cuisines: List<String>? = null
)
