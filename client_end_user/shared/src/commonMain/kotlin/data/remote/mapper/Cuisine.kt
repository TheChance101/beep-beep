package data.remote.mapper

import data.remote.model.CuisineDto
import domain.entity.Cuisine

fun CuisineDto.toCuisineEntity(): Cuisine {
    return Cuisine(
        cuisineId = id,
        cuisineName = name,
        cuisineImageUrl = ""
    )
}

fun List<CuisineDto>.toCuisineEntity() = map { it.toCuisineEntity() }