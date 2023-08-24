package data.remote.model

import domain.entity.Category

data class CategoryDto(
    val id: String,
    val name: String? = null,
)

fun CategoryDto.toEntity(): Category {
    return Category(
        id = id,
        name = name ?: ""
    )
}