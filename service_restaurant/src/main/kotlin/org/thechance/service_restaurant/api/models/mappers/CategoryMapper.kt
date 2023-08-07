package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.CategoryDto
import org.thechance.service_restaurant.domain.entity.Category


fun CategoryDto.toEntity() = Category(id = id ?: "", name = name)

fun Category.toDto() = CategoryDto(id = id, name = name)

fun List<Category>.toDto(): List<CategoryDto> = map { it.toDto() }