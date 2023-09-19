package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantOptionsDto(
    val page: Int?,
    val limit: Int?,
    val query: String?,
    val rating: Double?,
    val priceLevel: String?
)
