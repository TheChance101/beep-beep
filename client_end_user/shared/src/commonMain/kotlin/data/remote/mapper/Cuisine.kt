package data.remote.mapper

import data.remote.model.CuisineDto
import domain.entity.Cuisine

fun CuisineDto.toCuisineEntity(): Cuisine {
    return Cuisine(
        cuisineId = id,
        cuisineName = name,
        cuisineImageUrl = "https://icons.veryicon.com/png/Holiday/Christmas%205/fast%20food.png"
    )
}

fun List<CuisineDto>.toCuisineEntity() = map { it.toCuisineEntity() }