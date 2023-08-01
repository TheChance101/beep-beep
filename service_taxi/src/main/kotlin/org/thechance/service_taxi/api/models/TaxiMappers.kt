package org.thechance.service_taxi.api.models

import org.bson.types.ObjectId
import org.thechance.service_taxi.data.TaxiCollection
import org.thechance.service_taxi.domain.entity.Taxi

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


fun TaxiCollection.toTaxi(): Taxi {
    return Taxi(
        id = id.toHexString(),
        plateNumber = plateNumber,
        color = color,
        type = type,
        driverId = driverId?.toHexString(),
        isDeleted = isDeleted ?: false,
        isAvailable = isAvailable ?: true,
        capacity = capacity
    )
}

fun List<TaxiCollection>.toTaxes(): List<Taxi> = map(TaxiCollection::toTaxi)

fun Taxi.toCollection(): TaxiCollection {
    return TaxiCollection(
        id = if (id.isBlank()) ObjectId() else ObjectId(id),
        plateNumber = plateNumber,
        color = color,
        type = type,
        driverId = if (driverId != null) ObjectId(driverId) else null,
        isDeleted = isDeleted,
        isAvailable = isAvailable,
        capacity = capacity
    )
}