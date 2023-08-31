package org.thechance.service_taxi.api.dto.taxi

import org.bson.types.ObjectId
import org.thechance.service_taxi.data.collection.TaxiCollection
import org.thechance.service_taxi.domain.entity.Color
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.exceptions.CantBeNullException

fun TaxiDto.toEntity(): Taxi {
    return Taxi(
        id = if (id.isNullOrBlank()) ObjectId().toHexString() else ObjectId(id).toHexString(),
        plateNumber = plateNumber ?: "",
        color = color?.let { Color.getColorByColorNumber(it) } ?: Color.OTHER,
        type = type ?: "",
        driverId = driverId ?: "",
        isAvailable = isAvailable ?: true,
        seats = seats ?: 4,
    )
}

fun Taxi.toDto(): TaxiDto {
    return TaxiDto(
        id = id,
        plateNumber = plateNumber,
        color = color.colorNumber,
        type = type,
        driverId = driverId,
        isAvailable = isAvailable,
        seats = seats
    )
}

fun List<Taxi>.toDto(): List<TaxiDto> = map(Taxi::toDto)


fun TaxiCollection.toEntity(): Taxi {
    return Taxi(
        id = id.toString(),
        plateNumber = plateNumber ?: throw CantBeNullException,
        color = color?.let { Color.getColorByColorNumber(it) } ?: throw CantBeNullException,
        type = type ?: throw CantBeNullException,
        driverId = driverId?.toString() ?: throw CantBeNullException,
        isAvailable = isAvailable ?: true,
        seats = seats ?: 4
    )
}

fun List<TaxiCollection>.toEntity(): List<Taxi> = map(TaxiCollection::toEntity)

fun Taxi.toCollection(): TaxiCollection {
    return TaxiCollection(
        plateNumber = plateNumber,
        color = color.colorNumber,
        type = type,
        driverId = ObjectId(driverId),
        isAvailable = isAvailable,
        seats = seats,
        id = if (id.isNotBlank()) ObjectId(id) else ObjectId()
    )
}