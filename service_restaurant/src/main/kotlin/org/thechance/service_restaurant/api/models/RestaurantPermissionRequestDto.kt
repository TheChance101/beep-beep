package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantPermissionRequestDto(
    val id: String? = null,
    val restaurantName: String? = null,
    val ownerEmail: String? = null,
    val cause: String? = null,
)


