package data.remote.mapper

import data.remote.model.TripDto
import domain.entity.Location
import domain.entity.Trip

fun TripDto.toTripEntity(): Trip {
    return Trip(
        id = id,
        taxiId = taxiId ?: "",
        taxiPlateNumber = taxiPlateNumber ?: "",
        driverId = driverId ?: "",
        driverName = driverName ?: "",
        clientId = clientId,
        startPoint = startPoint?.toEntity() ?: Location(0.0, 0.0),
        destination = destination?.toEntity() ?: Location(0.0, 0.0),
        rate = rate ?: 0.0,
        price = price ?: 0.0,
        startDate = startDate ?: "",
        endDate = endDate ?: "",
        timeToArriveInMints = timeToArriveInMints ?: 0,
    )
}