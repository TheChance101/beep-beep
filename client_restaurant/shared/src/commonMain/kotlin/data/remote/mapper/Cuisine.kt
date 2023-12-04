package data.remote.mapper

import data.remote.model.CuisineDto
import data.remote.model.CuisineWithMealsDto
import domain.entity.Cuisine
import domain.entity.CuisineWithMeals

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
fun CuisineWithMealsDto.toEntity(): CuisineWithMeals {
    return CuisineWithMeals(
        id = id,
        name = name,
        imageUrl = image ?: "",
        meals = meals?.map { it.toEntity() } ?: emptyList()
    )
}

fun List<CuisineWithMealsDto>.toCuisinesWithMealsEntity() = map { it.toEntity() }