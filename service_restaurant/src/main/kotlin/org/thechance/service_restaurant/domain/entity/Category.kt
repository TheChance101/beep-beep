package org.thechance.service_restaurant.domain.entity

data class Category(
    val id: String,
    val name: String,
    val restaurants: List<Restaurant> = emptyList()
)

