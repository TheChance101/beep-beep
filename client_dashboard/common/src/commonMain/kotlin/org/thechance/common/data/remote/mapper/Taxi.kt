package org.thechance.common.data.remote.mapper

import org.thechance.common.data.remote.model.TaxiDto
import org.thechance.common.domain.entity.Taxi

fun TaxiDto.toEntity() = Taxi(
    id = id,
    plateNumber = plateNumber,
    color = color,
    type = type,
    seats = seats,
    status = status,
    trips = trips,
)

fun List<TaxiDto>.toEntity() = map { it.toEntity() }