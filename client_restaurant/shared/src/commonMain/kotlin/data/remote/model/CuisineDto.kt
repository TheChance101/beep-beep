package data.remote.model

import domain.entity.Cuisine

data class CuisineDto(val id: String, val name: String)


fun CuisineDto.toEntity(): Cuisine {
    return Cuisine(
        id = id,
        name = name
    )
}

fun List<CuisineDto>.toEntity() = map { it.toEntity() }
