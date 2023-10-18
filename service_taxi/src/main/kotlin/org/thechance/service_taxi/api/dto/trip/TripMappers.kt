package org.thechance.service_taxi.api.dto.trip

import kotlinx.datetime.LocalDateTime
import org.bson.types.ObjectId
import org.thechance.service_taxi.data.collection.LocationCollection
import org.thechance.service_taxi.data.collection.TripCollection
import org.thechance.service_taxi.data.collection.relationModel.TripWithTaxi
import org.thechance.service_taxi.domain.entity.Location
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.exceptions.CantBeNullException

fun TripDto.toEntity(): Trip {
    return Trip(
        id = ObjectId().toString(),
        taxiId = taxiId,
        driverId = driverId,
        clientId = clientId ?: throw CantBeNullException(),
        restaurantId = restaurantId,
        startPoint = startPoint?.toEntity() ?: throw CantBeNullException(),
        destination = destination?.toEntity() ?: throw CantBeNullException(),
        startPointAddress = startPointAddress ?: throw CantBeNullException(),
        destinationAddress = destinationAddress ?: throw CantBeNullException(),
        rate = rate,
        price = price ?: throw CantBeNullException(),
        startDate = startDate?.let { LocalDateTime.parse(it) },
        endDate = endDate?.let { LocalDateTime.parse(it) },
        isATaxiTrip = isATaxiTrip,
        tripStatus = Trip.getOrderStatus(tripStatus)
    )
}

fun LocationCollection.toEntity(): Location {
    return Location(
        latitude = latitude,
        longitude = longitude
    )
}

fun TripDto.LocationDto.toEntity(): Location {
    return Location(
        latitude = latitude,
        longitude = longitude
    )
}

fun Location.toDto(): TripDto.LocationDto {
    return TripDto.LocationDto(
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
        restaurantId = restaurantId,
        taxiDriverName = driverName,
        taxiPlateNumber = taxiPlateNumber ?: "",
        startPoint = startPoint.toDto(),
        destination = destination?.toDto() ?: TripDto.LocationDto(0.0, 0.0),
        startPointAddress = startPointAddress,
        destinationAddress = destinationAddress,
        rate = rate,
        price = price,
        startDate = startDate?.toString(),
        endDate = endDate?.toString(),
        isATaxiTrip = isATaxiTrip ?: true,
        tripStatus = tripStatus.statusCode
    )
}

fun List<Trip>.toDto(): List<TripDto> = map(Trip::toDto)

fun TripCollection.toEntity(): Trip {
    return Trip(
        id = id.toString(),
        taxiId = taxiId?.toString(),
        driverId = driverId?.toString(),
        clientId = clientId.toString(),
        restaurantId = restaurantId.toString(),
        startPoint = startPoint?.toEntity() ?: throw CantBeNullException(),
        destination = destination?.toEntity(),
        startPointAddress = startPointAddress,
        destinationAddress = destinationAddress,
        rate = rate,
        price = price ?: 0.0,
        startDate = startDate?.let { LocalDateTime.parse(it) },
        endDate = endDate?.let { LocalDateTime.parse(it) },
        isATaxiTrip = isATaxiTrip,
        tripStatus = Trip.getOrderStatus(tripStatus)
    )
}

fun List<TripCollection>.toEntity(): List<Trip> = map(TripCollection::toEntity)

fun Trip.toCollection(): TripCollection {
    return TripCollection(
        id = ObjectId(id),
        taxiId = if (taxiId != null) ObjectId(taxiId) else null,
        driverId = if (driverId != null) ObjectId(driverId) else null,
        clientId = ObjectId(clientId),
        restaurantId = if (restaurantId != null) ObjectId(restaurantId) else null,
        startPoint = startPoint.toCollection(),
        destination = destination?.toCollection(),
        startPointAddress = startPointAddress,
        destinationAddress = destinationAddress,
        rate = rate,
        price = price,
        startDate = startDate?.toString(),
        endDate = endDate?.toString(),
        isATaxiTrip = isATaxiTrip ?: true,
        tripStatus = tripStatus.statusCode
    )
}

fun TripWithTaxi.toEntity(): Trip {
    return Trip(
        id = id.toString(),
        taxiId = taxi.id.toString(),
        driverId = driverId?.toString() ?: "",
        taxiPlateNumber = taxi.plateNumber ?: "",
        driverName = taxi.driverUsername ?: "",
        clientId = clientId?.toString() ?: throw CantBeNullException(),
        restaurantId = restaurantId?.toString() ?: "",
        startPoint = startPoint?.toEntity() ?: throw CantBeNullException(),
        destination = destination?.toEntity(),
        startPointAddress = startPointAddress,
        destinationAddress = destinationAddress,
        rate = rate,
        price = price ?: 0.0,
        startDate = startDate?.let { LocalDateTime.parse(it) },
        endDate = endDate?.let { LocalDateTime.parse(it) },
        tripStatus = Trip.getOrderStatus(tripStatus)
    )
}