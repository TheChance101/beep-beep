package org.thechance.api_gateway.data.model.restaurant

import org.thechance.api_gateway.data.model.CuisineResource

data class RestaurantResource(
    val id: String,
    val ownerId: String,
    val name: String,
    val description: String? = null,
    val priceLevel: String? = null,
    val rate: Double? = null,
    val phone: String,
    val openingTime: String,
    val closingTime: String,
    val location: LocationResource,
    val address: String,
    val cuisines: List<CuisineResource> = emptyList()
)