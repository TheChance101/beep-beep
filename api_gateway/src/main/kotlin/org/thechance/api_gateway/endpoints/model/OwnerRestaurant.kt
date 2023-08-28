package org.thechance.api_gateway.endpoints.model

data class OwnerRestaurant(
    val name: String,
    val description: String,
    val priceLevel: String,
    val rate: Double,
    val currency: String,
    val phone: String,
    val openingTime: String,
    val closingTime: String,
)
