package org.thechance.service_restaurant.domain.entity

data class RestaurantOptions(
    val page: Int,
    val limit: Int,
    val query: String?,
    val rating: Double?,
    val priceLevel: String?
)