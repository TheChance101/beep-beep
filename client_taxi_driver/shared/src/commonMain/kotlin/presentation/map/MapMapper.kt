package presentation.map

import domain.entity.Order

fun Order.toUiState() = OrderInfoUiState(
    passengerName = passengerName,
    dropOffAddress = LocationInfoUiState(name = dropOffAddress),
    pickUpAddress = LocationInfoUiState(name = pickUpAddress),
)