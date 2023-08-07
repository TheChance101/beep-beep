package org.thechance.service_taxi.api.models.trip

import kotlinx.datetime.LocalDateTime
import org.bson.types.ObjectId
import org.thechance.service_taxi.data.collection.LocationCollection
import org.thechance.service_taxi.data.collection.TripCollection
import org.thechance.service_taxi.domain.entity.Location
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.entity.TripUpdateRequest
import org.thechance.service_taxi.domain.util.CantBeNullException

fun TripDto.toEntity(): Trip {
    return Trip(
        id = if (id.isNullOrBlank()) ObjectId().toHexString() else ObjectId(id).toHexString(),
        taxiId = taxiId,
        driverId = driverId,
        clientId = clientId ?: throw CantBeNullException,
        startPoint = startPoint?.toEntity() ?: throw CantBeNullException,
        destination = destination?.toEntity() ?: throw CantBeNullException,
        rate = rate,
        price = price ?: throw CantBeNullException,
        startDate = startDate?.let { LocalDateTime.parse(it) },
        endDate = endDate?.let { LocalDateTime.parse(it) },
    )
}

fun LocationCollection.toEntity(): Location {
    return Location(
        latitude = latitude,
        longitude = longitude
    )
}

fun Location.toCollection(): LocationCollection {
    return LocationCollection(
        latitude = latitude,
        longitude = longitude
    )
}

fun Trip.toDto(): TripDto {
    return TripDto(
        id = id,
        taxiId = taxiId,
        driverId = driverId,
        clientId = clientId,
        startPoint = startPoint.toCollection(),
        destination = destination?.toCollection(),
        rate = rate,
        price = price,
        startDate = startDate?.toString(),
        endDate = endDate?.toString()
    )
}

fun List<Trip>.toDto(): List<TripDto> = map(Trip::toDto)

fun TripCollection.toEntity(): Trip {
    return Trip(
        id = id.toHexString(),
        taxiId = taxiId?.toHexString(),
        driverId = driverId?.toHexString(),
        clientId = clientId?.toHexString() ?: throw CantBeNullException,
        startPoint = startPoint?.toEntity() ?: throw CantBeNullException,
        destination = destination?.toEntity(),
        rate = rate,
        price = price ?: 0.0,
        startDate = startDate,
        endDate = endDate
    )
}

fun List<TripCollection>.toEntity(): List<Trip> = map(TripCollection::toEntity)

fun Trip.toCollection(): TripCollection {
    return TripCollection(
        taxiId = if (taxiId != null) ObjectId(taxiId) else null,
        driverId = if (driverId != null) ObjectId(driverId) else null,
        clientId = ObjectId(clientId),
        startPoint = startPoint.toCollection(),
        destination = destination?.toCollection(),
        rate = rate,
        price = price,
        startDate = startDate,
        endDate = endDate
    )
}

fun TripUpdateRequest.toCollection(): TripCollection {
    return TripCollection(
        taxiId = taxiId?.let { ObjectId(it) },
        driverId = driverId?.let { ObjectId(it) },
        clientId = clientId?.let { ObjectId(it) },
        startPoint = startPoint?.toCollection(),
        destination = destination?.toCollection(),
        rate = rate,
        price = price,
        startDate = startDate,
        endDate = endDate,
    )
}
