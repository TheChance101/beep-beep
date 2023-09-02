package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: String? = null,
    val name: String?= null,
)