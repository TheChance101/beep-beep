package data.remote.mapper

import data.remote.model.CuisineDto
import domain.entity.Cuisine

fun CuisineDto.toCuisineEntity(): Cuisine {
    return Cuisine(
        id = id,
        name = name,
        imageUrl = "https://icons.veryicon.com/png/Holiday/Christmas%205/fast%20food.png"
    )
}

fun List<CuisineDto>.toCuisineEntity() = map { it.toCuisineEntity() }
