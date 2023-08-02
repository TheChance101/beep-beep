package org.thechance.service_restaurant.entity

data class Cuisine(
    val id: String,
    val name: String? = null,
    val isDeleted: Boolean = false
)
