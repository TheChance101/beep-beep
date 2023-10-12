package data.remote.mapper

import data.remote.model.TripDto
import domain.entity.Trip

fun TripDto.toTripEntity(): Trip {
    return Trip(
        id = id,
        restaurantName = restaurantName,
        restaurantImage = restaurantImage,
        startPoint = startPoint.toEntity(),
        destination = destination.toEntity(),
        startPointAddress = startPointAddress,
        destinationAddress = destinationAddress,
        price = price,
        tripStatus = tripStatus
    )
}