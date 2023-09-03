package data.remote.mapper

import data.remote.model.CuisineDto
import domain.entity.Cuisine

fun CuisineDto.toEntity(): Cuisine {
    return Cuisine(
        id = id,
        name = name
    )
}

fun Cuisine.toDto(): CuisineDto {
    return CuisineDto(
        id = id,
        name = name
    )
}

fun List<CuisineDto>.toEntity() = map { it.toEntity() }

fun List<Cuisine>.toDto() = map { it.toDto() }