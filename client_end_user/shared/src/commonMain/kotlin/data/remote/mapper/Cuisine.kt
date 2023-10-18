package data.remote.mapper

import data.remote.model.CuisineDto
import domain.entity.Cuisine

fun CuisineDto.toCuisineEntity() = Cuisine(
    id = id,
    name = name,
    meals = meals?.toEntity(),
    imageUrl = if (image.isNullOrBlank()) {
        "https://icons.veryicon.com/png/Holiday/Christmas%205/fast%20food.png"
    } else {
        image
    }
)


fun List<CuisineDto>.toCuisineEntity() = map { it.toCuisineEntity() }
