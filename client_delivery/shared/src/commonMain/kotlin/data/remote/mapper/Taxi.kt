package data.remote.mapper

import data.remote.model.TaxiDto
import domain.entity.Taxi

fun TaxiDto.toEntity(): Taxi {
    return Taxi(
        id = id ?: "",
        plateNumber = plateNumber,
        color = color,
        type = type,
        driverId = driverId ?: "",
        driverUsername = driverUsername,
        driverImage = driverImage ?: "",
        rate = rate ?: 0.0,
        isAvailable = isAvailable,
        seats = seats,
        tripsCount = tripsCount ?: 0
    )
}