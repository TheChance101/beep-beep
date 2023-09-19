package data.remote.mapper

import data.remote.model.LocationDto
import data.remote.model.TripDto
import domain.entity.Trip

fun TripDto.toTripEntity(): Trip {
    return Trip(
        id = id,
        taxiId = taxiId ?: "",
        taxiPlateNumber = taxiPlateNumber ?: "",
        driverId = driverId ?: "",
        clientId = clientId,
        startPoint = startPoint ?: LocationDto(),
        destination = destination ?: LocationDto(),
        rate = rate ?: 0.0,
        price = price ?: 0.0,
        startDate = startDate ?: "",
        endDate = endDate ?: "",
        timeToArriveInMints = timeToArriveInMints ?: 0,
    )
}