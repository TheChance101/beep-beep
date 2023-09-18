package presentation.map

import domain.entity.Location
import domain.entity.Order

fun Order.toUiState() = OrderInfoUiState(
    passengerName = passengerName,
    dropOffAddress = LocationInfoUiState(name = dropOffAddress),
    pickUpAddress = LocationInfoUiState(name = pickUpAddress),
)

fun Location.toUiState() = LocationInfoUiState(
    lat = lat,
    lng = lng,
    name = name,
)