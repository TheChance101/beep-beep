package org.thechance.api_gateway.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val id: String,
    val ownerId: String,
    val name: String,
    val description: String,
    val priceLevel: String,
    val rate: Double,
    val phone: String,
    val openingTime: String,
    val closingTime: String,
    val location: Location,
    val address: String,
    val cuisines: List<Cuisine> = emptyList()
)