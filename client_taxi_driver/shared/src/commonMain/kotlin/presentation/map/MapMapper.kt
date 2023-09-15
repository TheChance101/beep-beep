package presentation.map

import domain.entity.Order

fun Order.toUiState() = OrderInfoUiState(
    passengerName = passengerName,
    dropOffAddress = dropOffAddress,
    pickUpAddress = pickUpAddress,
)