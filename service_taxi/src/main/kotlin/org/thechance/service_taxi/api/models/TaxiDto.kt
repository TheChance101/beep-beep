package org.thechance.service_taxi.api.models

import kotlinx.serialization.Serializable
import org.thechance.service_taxi.domain.entity.Taxi

@Serializable
data class TaxiDto(
    val id: String,
    val plateNumber: String,
    val color: String,
    val type: String,
    val driverId: String,
    val isAvailable: Boolean = true,
    val capacity: Int,
)

// region mappers
fun Taxi.toDto(): TaxiDto {
    return TaxiDto(
        id = id,
        plateNumber = plateNumber,
        color = color,
        type = type,
        driverId = driverId,
        isAvailable = isAvailable,
        capacity = capacity
    )
}

fun List<Taxi>.toDto(): List<TaxiDto> = map(Taxi::toDto)
// endregion