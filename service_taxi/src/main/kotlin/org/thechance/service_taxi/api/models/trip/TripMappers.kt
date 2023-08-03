package org.thechance.service_taxi.api.models.trip

import org.bson.types.ObjectId
import org.thechance.service_taxi.data.collection.TripCollection
import org.thechance.service_taxi.domain.entity.Trip

fun TripDto.toEntity(): Trip {
    return Trip(
        id = if (id.isNullOrBlank()) ObjectId().toHexString() else ObjectId(id).toHexString(),
        driverId = driverId,
        clientId = clientId,
        from = from,
        to = to,
        rate = rate,
        price = price
    )
}

fun Trip.toDto(): TripDto {
    return TripDto(
        id = id,
        driverId = driverId,
        clientId = clientId,
        from = from,
        to = to,
        rate = rate,
        price = price
    )
}

fun List<Trip>.toDto(): List<TripDto> = map(Trip::toDto)

fun TripCollection.toEntity(): Trip {
    return Trip(
        id = id.toHexString(),
        driverId = driverId?.toHexString(),
        clientId = clientId?.toHexString(),
        from = from,
        to = to,
        rate = rate,
        price = price,
        isDeleted = isDeleted
    )
}

fun List<TripCollection>.toEntity(): List<Trip> = map(TripCollection::toEntity)

fun Trip.toCollection(): TripCollection {
    return TripCollection(
        driverId = if (driverId != null) ObjectId(driverId) else null,
        clientId = if (clientId != null) ObjectId(clientId) else null,
        from = from,
        to = to,
        rate = rate,
        price = price,
        isDeleted = isDeleted
    )
}
