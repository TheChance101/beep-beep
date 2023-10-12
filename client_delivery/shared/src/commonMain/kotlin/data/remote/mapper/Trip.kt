package data.remote.mapper

import data.remote.model.TripDto
import domain.entity.Location
import domain.entity.Trip

fun TripDto.toTripEntity(): Trip {
    return Trip(
        id = id ?: "",
        restaurantName = restaurantName?: "",
        restaurantImage = restaurantImage ?: "",
        startPoint = startPointAddress?.toEntity() ?: Location(0.0, 0.0),
        destination = destinationAddress?.toEntity() ?: Location(0.0, 0.0),
        tripStatus = tripStatus
    )
}