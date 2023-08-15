package org.thechance.service_restaurant.domain.entity

data class Restaurant(
    val id: String,
    val ownerId: String,
    val name: String,
    val description: String? = null,
    val priceLevel: String?=null,
    val rate: Double?= null,
    val phone: String,
    val openingTime: String,
    val closingTime: String,
    val address: Location,
    val cuisines: List<Cuisine> = emptyList()
)