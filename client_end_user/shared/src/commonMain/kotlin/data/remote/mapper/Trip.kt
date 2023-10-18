package data.remote.mapper

import data.remote.model.DeliveryRideDto
import data.remote.model.TaxiRideDto
import data.remote.model.TripDto
import domain.entity.DeliveryRide
import domain.entity.Location
import domain.entity.TaxiRide
import domain.entity.Trip
import kotlinx.datetime.LocalTime

fun TripDto.toTripEntity(): Trip {
    return Trip(
        id = id ?: "",
        taxiId = taxiId ?: "",
        driverId = driverId ?: "",
        clientId = clientId ?: "",
        restaurantId = restaurantId ?: "",
        taxiPlateNumber = if (taxiPlateNumber.isNullOrBlank()) "1346FV" else taxiPlateNumber,
        taxiDriverName = taxiDriverName ?: "",
        startPoint = startPoint?.toEntity() ?: Location(0.0, 0.0),
        destination = destination?.toEntity() ?: Location(0.0, 0.0),
        startPointAddress = startPointAddress ?: "",
        destinationAddress = destinationAddress ?: "",
        rate = rate ?: 0.0,
        price = price ?: 0.0,
        startDate = startDate,
        endDate = endDate,
        isATaxiTrip = isATaxiTrip ?: false,
        tripStatus = Trip.TripStatus.getTripStatus(tripStatus),
        timeToArriveInMints = LocalTime(0, 30).minute,
    )
}

fun List<TripDto>.toTripEntity() = map { it.toTripEntity() }

fun TaxiRideDto.toTaxiRideEntity(): TaxiRide {
    return TaxiRide(
        id = id ?: "",
        taxiPlateNumber = taxiPlateNumber,
        taxiDriverName = taxiDriverName,
        driverImage = driverImage ?: "",
        carType = carType ?: "",
        startPoint = startPoint.toEntity(),
        destination = destination.toEntity(),
        startPointAddress = startPointAddress,
        destinationAddress = destinationAddress,
        rate = rate ?: 0.0,
        tripStatus = Trip.TripStatus.getTripStatus(tripStatus),
    )
}

fun DeliveryRideDto.toDeliveryRideEntity(): DeliveryRide {
    return DeliveryRide(
        id = id,
        startPoint = startPoint.toEntity(),
        destination = destination.toEntity(),
        status = status,
    )
}