package org.thechance.service_taxi.api.models

import kotlinx.serialization.Serializable
import org.thechance.service_taxi.domain.entity.Taxi

@Serializable
data class TaxiDto(
    val id: String,
    val name: String?,
)

// region mappers
fun Taxi.toDto(): TaxiDto {
    return TaxiDto(
        id = id.toHexString(),
        name = name
    )
}

fun List<Taxi>.toDto(): List<TaxiDto> = map(Taxi::toDto)
// endregion