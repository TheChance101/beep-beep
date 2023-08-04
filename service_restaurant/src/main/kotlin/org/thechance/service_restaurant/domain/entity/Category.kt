package org.thechance.service_restaurant.domain.entity

data class Category(
    val id: String? = null,
    val name: String? = null,
    val isDeleted: Boolean = false,
    val restaurants: List<Restaurant>? = null
)

