package org.thechance.common.data.gateway.remote.mapper

import org.thechance.common.data.gateway.remote.model.CuisineDto
import org.thechance.common.domain.entity.Cuisine


fun CuisineDto.toEntity() = Cuisine(
    id = id,
    name = name,
    image = image
)
fun Cuisine.toDto() = CuisineDto(
    id = id ,
    name = name,
    image = image
)

fun List<CuisineDto>.toEntity() = map(CuisineDto::toEntity)