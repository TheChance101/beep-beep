package presentation.map

import domain.entity.Location
import domain.entity.Order

fun Order.toUiState() = OrderInfoUiState(
    passengerName = passengerName,
    dropOffAddress = LocationInfoUiState(
        addressName = dropOffAddress.addressName,
        lat = dropOffAddress.lat,
        lng = dropOffAddress.lng,
    ),
    pickUpAddress = LocationInfoUiState(
        addressName = pickUpAddress.addressName,
        lat = pickUpAddress.lat,
        lng = pickUpAddress.lng,
    ),
)

fun Location.toUiState() = LocationInfoUiState(
    lat = lat,
    lng = lng,
    addressName = addressName,
)

fun LocationInfoUiState.toEntity(): Location = Location(
    lat = lat,
    lng = lng,
    addressName = addressName,
)