package data.remote.mapper

import data.remote.model.CategoryDto
import domain.entity.Category

fun CategoryDto.toEntity(): Category {
    return Category(
        id = id,
        name = name ?: ""
    )
}