package data.remote.mapper

import data.remote.model.TripDto
import domain.entity.Location
import domain.entity.Trip
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalDate

fun TripDto.toTripEntity(): Trip {
    return Trip(
        id = id ?: "",
        taxiId = taxiId ?: "",
        driverId = driverId ?: "",
        clientId = clientId ?: "",
        restaurantId = restaurantId ?: "",
        taxiPlateNumber = taxiPlateNumber ?: "",
        taxiDriverName = taxiDriverName ?: "",
        startPoint = startPoint?.toEntity() ?: Location(0.0, 0.0),
        destination = destination?.toEntity() ?: Location(0.0, 0.0),
        startPointAddress = startPointAddress ?: "",
        destinationAddress = destinationAddress ?: "",
        rate = rate ?: 0.0,
        price = price ?: 0.0,
        startDate = startDate?.toLocalDate() ?: LocalDate(2023, 10, 17),
        endDate = startDate?.toLocalDate(),
        isATaxiTrip = isATaxiTrip ?: false,
        tripStatus = tripStatus,
        timeToArriveInMints = LocalTime(0, 30).minute,
    )
}
