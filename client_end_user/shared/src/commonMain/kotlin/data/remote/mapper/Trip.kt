package data.remote.mapper

import data.remote.model.DeliveryRideDto
import data.remote.model.TaxiRideDto
import data.remote.model.TripDto
import domain.entity.DeliveryRide
import domain.entity.Location
import domain.entity.TaxiColor
import domain.entity.TaxiRide
import domain.entity.Trip
import domain.entity.TripStatus
import kotlinx.datetime.LocalTime

fun TripDto.toTripEntity(): Trip {
    return Trip(
        id = id ?: "",
        taxiId = taxiId ?: "",
        driverId = driverId ?: "",
        clientId = clientId ?: "",
        orderId = orderId ?: "",
        restaurantId = restaurantId ?: "",
        taxiPlateNumber = taxiPlateNumber ?: "",
        taxiDriverName = taxiDriverName ?: "",
        taxiColor = TaxiColor.getColorByColorNumber(taxiColor ?: 4294639360L),
        startPoint = startPoint?.toEntity() ?: Location(0.0, 0.0),
        destination = destination?.toEntity() ?: Location(0.0, 0.0),
        startPointAddress = startPointAddress ?: "",
        destinationAddress = destinationAddress ?: "",
        rate = rate ?: 0.0,
        price = price ?: 0.0,
        startDate = startDate,
        endDate = endDate,
        isATaxiTrip = isATaxiTrip ?: false,
        tripStatus = TripStatus.getTripStatus(tripStatus),
        timeToArriveInMints = LocalTime(0, 30).minute,
    )
}

fun List<TripDto>.toTripEntity() = map { it.toTripEntity() }

fun TaxiRideDto.toTaxiRideEntity(): TaxiRide {
    return TaxiRide(
        id = id,
        taxiPlateNumber = taxiPlateNumber,
        taxiDriverName = taxiDriverName,
        driverImage = driverImage ?: "",
        carType = carType ?: "",
        taxiColor = TaxiColor.getColorByColorNumber(taxiColor),
        startPoint = startPoint.toEntity(),
        destination = destination.toEntity(),
        startPointAddress = startPointAddress,
        destinationAddress = destinationAddress,
        rate = rate ?: 0.0,
        tripStatus = TripStatus.getTripStatus(tripStatus),
    )
}

fun DeliveryRideDto.toDeliveryRideEntity(): DeliveryRide {
    return DeliveryRide(
        id = id,
        restaurantName = restaurantName,
        restaurantImage = restaurantImage.ifEmpty {
            "https://graphicsfamily.com/wp-content/uploads/edd/2023/02/Restaurant-Logo-Design-2-scaled.jpg"
        },
        startPoint = startPoint.toEntity(),
        destination = destination.toEntity(),
        startPointAddress = startPointAddress,
        destinationAddress = destinationAddress,
        price = price,
        tripStatus = TripStatus.getTripStatus(tripStatus),
    )
}