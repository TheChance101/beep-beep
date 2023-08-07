package org.thechance.service_taxi.api.models.taxi

import org.bson.types.ObjectId
import org.thechance.service_taxi.data.collection.TaxiCollection
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.TaxiUpdateRequest
import org.thechance.service_taxi.domain.util.CantBeNullException

fun TaxiDto.toEntity(): Taxi {
    return Taxi(
        id = if (id.isNullOrBlank()) ObjectId().toHexString() else ObjectId(id).toHexString(),
        plateNumber = plateNumber ?: throw CantBeNullException,
        color = color ?: throw CantBeNullException,
        type = type ?: throw CantBeNullException,
        driverId = driverId ?: throw CantBeNullException,
        isAvailable = isAvailable ?: throw CantBeNullException,
        seats = seats ?: 4,
    )
}

fun Taxi.toDto(): TaxiDto {
    return TaxiDto(
        id = id,
        plateNumber = plateNumber,
        color = color,
        type = type,
        driverId = driverId,
        isAvailable = isAvailable,
        seats = seats
    )
}

fun List<Taxi>.toDto(): List<TaxiDto> = map(Taxi::toDto)


fun TaxiCollection.toEntity(): Taxi {
    return Taxi(
        id = id.toHexString(),
        plateNumber = plateNumber ?: throw CantBeNullException,
        color = color ?: throw CantBeNullException,
        type = type ?: throw CantBeNullException,
        driverId = driverId?.toHexString() ?: throw CantBeNullException,
        isAvailable = isAvailable ?: true,
        seats = seats ?: 4
    )
}

fun List<TaxiCollection>.toEntity(): List<Taxi> = map(TaxiCollection::toEntity)

fun Taxi.toCollection(): TaxiCollection {
    return TaxiCollection(
        plateNumber = plateNumber,
        color = color,
        type = type,
        driverId = ObjectId(driverId),
        isAvailable = isAvailable,
        seats = seats
    )
}


fun TaxiDto.toUpdateRequest(): TaxiUpdateRequest {
    return TaxiUpdateRequest(
        id = id ?: "",
        plateNumber = plateNumber,
        color = color,
        type = type,
        driverId = driverId,
        isAvailable = isAvailable,
        seats = seats,
    )
}

fun TaxiUpdateRequest.toCollection(): TaxiCollection {
    return TaxiCollection(
        plateNumber = plateNumber,
        color = color,
        type = type,
        driverId = driverId?.let { ObjectId(it) },
        isAvailable = isAvailable,
        seats = seats,
    )
}