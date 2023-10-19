package data.remote.mapper

import data.remote.model.TripDto
import domain.entity.Location
import domain.entity.Price
import domain.entity.Trip
import kotlinx.datetime.LocalDate

//TODO Add currency
//FIX startDate to be now if null
fun TripDto.toTripEntity(): Trip {
    return Trip(
        id = id,
        taxiId = taxiId ?: "",
        taxiPlateNumber = taxiPlateNumber ?: "",
        driverId = driverId ?: "",
        driverName = taxiDriverName ?: "",
        clientId = clientId,
        startPoint = startPoint?.toEntity() ?: Location(0.0, 0.0),
        destination = destination?.toEntity() ?: Location(0.0, 0.0),
        rate = rate ?: 0.0,
        price = Price(price ?: 0.0, "$"),
        startDate = startDate?.toDate() ?: LocalDate(2023, 9, 23),
        endDate = endDate?.toDate(),
        timeToArriveInMints = timeToArriveInMints ?: 0,
    )
}
