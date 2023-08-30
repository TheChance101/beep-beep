package org.thechance.api_gateway.data.model.restaurant

import kotlinx.serialization.Serializable
import org.thechance.api_gateway.data.model.CuisineResource

@Serializable
data class RestaurantResource(
    val id: String? = null,
    val ownerId: String? = null,
    val name: String? = null,
    val description: String? = null,
    val priceLevel: String? = null,
    val rate: Double? = null,
    val phone: String? = null,
    val openingTime: String? = null,
    val closingTime: String? = null,
    val location: LocationResource? = null,
    val address: String? = null,
    val cuisines: List<CuisineResource> = emptyList()
)