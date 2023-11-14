package presentation.map

import domain.entity.Location
import domain.entity.Trip

fun Trip.toUiState() = TripInfoUiState(
    id = this.id,
    passengerName = passengerName,
    dropOffLocation = this.dropOffLocation.toUiState(),
    pickUpLocation = this.pickUpLocation.toUiState(),
    dropOffAddress = this.dropOffAddress,
    pickUpAddress = this.pickUpAddress
)

fun Location.toUiState() = LocationInfoUiState(
    lat = latitude,
    lng = longitude,
)

fun LocationInfoUiState.toEntity(): Location = Location(
    latitude = lat,
    longitude = lng,
)