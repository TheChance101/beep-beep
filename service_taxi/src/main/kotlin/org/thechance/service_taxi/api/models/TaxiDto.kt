package org.thechance.service_taxi.api.models

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.thechance.service_taxi.domain.entity.Taxi

@Serializable
data class TaxiDto(
    val id: String? = null,
    val plateNumber: String? = null,
    val color: String? = null,
    val type: String? = null,
    val driverId: String? = null,
    val isAvailable: Boolean? = null,
    val capacity: Int? = null,
)

// region mappers
fun TaxiDto.toTaxi(): Taxi {
    return Taxi(
        id = if (id.isNullOrBlank()) ObjectId().toHexString() else ObjectId(id).toHexString(),
        plateNumber = plateNumber,
        color = color,
        type = type,
        driverId = driverId,
        isAvailable = isAvailable,
        capacity = capacity,
    )
}

fun Taxi.toDto(): TaxiDto {
    return TaxiDto(
        id = id,
        plateNumber = plateNumber,
        color = color,
        type = type,
        driverId = driverId,
        isAvailable = isAvailable ?: true,
        capacity = capacity ?: 1
    )
}

fun List<Taxi>.toDto(): List<TaxiDto> = map(Taxi::toDto)
// endregion