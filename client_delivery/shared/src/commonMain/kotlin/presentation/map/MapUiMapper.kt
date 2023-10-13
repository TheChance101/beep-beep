package presentation.map

import domain.entity.Location

fun Location.toUiState() = LocationUiState(
    lat = latitude,
    lng = longitude,
    addressName = "",
)

fun LocationUiState.toEntity(): Location = Location(
    latitude = lat,
    longitude = lng,
)