package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.CuisineDetailsDto
import org.thechance.service_restaurant.api.models.CuisineDto
import org.thechance.service_restaurant.domain.entity.Cuisine

fun Cuisine.toDto(): CuisineDto = CuisineDto(id, name)

fun List<Cuisine>.toDto(): List<CuisineDto> = this.map { it.toDto() }

fun CuisineDto.toEntity(): Cuisine = Cuisine(id ?: "", name ?: "", image ?: "")

fun Cuisine.toCuisineDetailsDto() = CuisineDetailsDto(id, name, image = image, meals = meals.toMealDto())

fun List<Cuisine>.toCuisineDetailsDto() = map { it.toCuisineDetailsDto() }