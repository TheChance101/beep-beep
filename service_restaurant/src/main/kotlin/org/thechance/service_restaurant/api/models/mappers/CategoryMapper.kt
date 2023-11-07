package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.CategoryDetailsDto
import org.thechance.service_restaurant.api.models.CategoryDto
import org.thechance.service_restaurant.domain.entity.Category


fun CategoryDto.toEntity() = Category(id = id ?: "", name = name ?: "", image = image ?: "")

fun Category.toCategoryRestaurantsDto() = CategoryDetailsDto(id = id, name = name, restaurants = restaurants.toDto())

fun List<Category>.toCategoryRestaurantsDto(): List<CategoryDetailsDto> = map { it.toCategoryRestaurantsDto() }


fun Category.toDto() = CategoryDto(id = id, name = name, image = image)

fun List<Category>.toDto(): List<CategoryDto> = map { it.toDto() }