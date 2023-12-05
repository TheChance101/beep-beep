package presentation.map

import domain.entity.Location
import domain.entity.Trip

fun Trip.toUiState() = TripInfoUiState(
    id = this.id,
    passengerName = passengerName,
    dropOffLocation = dropOffLocation.toUiState(),
    pickUpLocation = pickUpLocation.toUiState(),
    dropOffAddress = dropOffAddress,
    pickUpAddress = pickUpAddress
)

fun Location.toUiState() = LocationInfoUiState(
    lat = latitude,
    lng = longitude,
)

fun LocationInfoUiState.toEntity(): Location = Location(
    latitude = lat,
    longitude = lng,
)