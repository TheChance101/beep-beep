package data.remote.mapper

import data.remote.model.LocationDto
import data.remote.model.TripDto
import domain.entity.Location
import domain.entity.Trip

fun TripDto.toEntity(): Trip = Trip(
    id = id ?: "",
    passengerName = this.clientName ?: "",
    dropOffLocation = this.destination.toEntity(),
    pickUpLocation = this.startPoint.toEntity(),
    pickUpAddress = this.startPointAddress,
    dropOffAddress = this.destinationAddress
)

fun LocationDto.toEntity(): Location = Location(
    latitude = this.latitude ?: 0.0,
    longitude = this.longitude ?: 0.0,
)

fun Location.toDto(): LocationDto = LocationDto(
    latitude = this.latitude,
    longitude = this.longitude,
)