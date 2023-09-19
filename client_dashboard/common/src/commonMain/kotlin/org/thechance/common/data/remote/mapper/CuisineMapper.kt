package org.thechance.common.data.remote.mapper

import org.thechance.common.data.remote.model.CuisineDto
import org.thechance.common.domain.entity.Cuisine


fun CuisineDto.toEntity() = Cuisine(
    id = id,
    name = name
)

fun List<CuisineDto>.toEntity() = map(CuisineDto::toEntity)