package org.thechance.service_restaurant.domain.entity

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
    val address: Address,
    val cuisines: List<Cuisine> = emptyList()
)