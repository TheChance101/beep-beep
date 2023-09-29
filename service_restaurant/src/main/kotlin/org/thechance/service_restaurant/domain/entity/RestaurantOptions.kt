package org.thechance.service_restaurant.domain.entity

data class RestaurantOptions(
    val page: Int,
    val limit: Int,
    val query: String? = null,
    val rating: Double? = null,
    val priceLevel: String? = null
)