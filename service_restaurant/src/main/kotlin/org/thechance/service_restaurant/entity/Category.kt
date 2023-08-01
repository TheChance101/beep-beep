package org.thechance.service_restaurant.entity

import org.thechance.service_restaurant.api.models.CategoryDto
import org.thechance.service_restaurant.data.collection.CategoryCollection

data class Category(
    val id: String? = null,
    val name: String? = null,
    val isDeleted: Boolean = false,
    val restaurants: List<Restaurant>? = null
) {
    fun toDto(): CategoryDto {
        return CategoryDto(
            id = id,
            name = name,
        )
    }

    fun toCollection(): CategoryCollection {
        return CategoryCollection(
            name = name,
        )
    }
}

fun List<Category>.toDto(): List<CategoryDto> = map { it.toDto() }