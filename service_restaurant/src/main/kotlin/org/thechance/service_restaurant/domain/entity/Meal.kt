package org.thechance.service_restaurant.domain.entity


data class Meal(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val price: Int? = null,
    val isDeleted: Boolean = false,
    val cuisines: List<Cuisine>? = null
)
