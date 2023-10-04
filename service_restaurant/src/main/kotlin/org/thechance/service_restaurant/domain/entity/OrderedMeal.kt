package org.thechance.service_restaurant.domain.entity

data class OrderedMeal(
    val meadId: String,
    val name: String,
    val image: String,
    val quantity: Int,
    val price: Double
)