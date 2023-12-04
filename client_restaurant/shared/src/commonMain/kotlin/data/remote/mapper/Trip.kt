package data.remote.mapper

import data.remote.model.TripDto
import domain.entity.Location
import domain.entity.Trip

fun Trip.toDto():TripDto{
    return TripDto(
        clientId = clientId,
        orderId = orderId,
        restaurantId = restaurantId,
        startPoint = startPoint.toDto(),
        destination = destination.toDto(),
        startPointAddress = startPointAddress,
        destinationAddress = destinationAddress,
        price = price,
        isATaxiTrip = isATaxiTrip,
    )
}
fun TripDto.toEntity():Trip{
    return Trip(
        clientId = clientId ?: "",
        orderId = orderId ?: "",
        restaurantId = restaurantId ?: "",
        startPoint = startPoint?.toEntity() ?: Location(0.0,0.0),
        destination = destination?.toEntity() ?: Location(0.0,0.0),
        startPointAddress = startPointAddress ?: "",
        destinationAddress = destinationAddress ?: "",
        price = price ?: 0.0,
        isATaxiTrip = isATaxiTrip ?: false,
    )
}