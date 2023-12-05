package data.remote.mapper

import data.remote.model.LocationDto
import data.remote.model.TaxiTripDto
import domain.entity.Location
import domain.entity.Trip

fun TaxiTripDto.toEntity(): Trip = Trip(
    id = id ?: "",
    passengerName = this.clientName ?: "",
    dropOffLocation = this.destination?.toEntity()?: Location(0.0, 0.0),
    pickUpLocation = this.startPoint?.toEntity()?: Location(0.0, 0.0),
    pickUpAddress = this.startPointAddress?:"",
    dropOffAddress = this.destinationAddress?:"",
)

fun LocationDto.toEntity(): Location = Location(
    latitude = this.latitude ?: 0.0,
    longitude = this.longitude ?: 0.0,
)

fun Location.toDto(): LocationDto = LocationDto(
    latitude = this.latitude,
    longitude = this.longitude,
)