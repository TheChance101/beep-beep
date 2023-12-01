package data.remote.mapper

import data.remote.model.CuisineDto
import domain.entity.Cuisine

fun CuisineDto.toCuisineEntity() = Cuisine(
    id = id,
    name = name,
    meals = meals?.toEntity(),
    imageUrl = image ?: ""
)

fun CuisineDto.toEntity() = Cuisine(
    id = id,
    name = name,
    meals = emptyList(),
    imageUrl = image ?: ""
)

fun List<CuisineDto>.toEntity() = map { it.toEntity() }

fun List<CuisineDto>.toCuisineEntity() = map { it.toCuisineEntity() }
