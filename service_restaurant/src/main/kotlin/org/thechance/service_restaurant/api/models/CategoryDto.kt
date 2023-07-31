package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable
import org.thechance.service_restaurant.entity.Category

@Serializable
data class CategoryDto(
    val id: String? = null,
    val name: String? = null
) {
    fun toEntity(): Category {
        return Category(
            id = id,
            name = name
        )
    }
}