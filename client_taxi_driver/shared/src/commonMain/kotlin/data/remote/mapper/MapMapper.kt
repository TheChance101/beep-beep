package data.remote.mapper

import data.remote.model.LocationDto
import data.remote.model.OrderDto
import domain.entity.Location
import domain.entity.Order

fun OrderDto.toEntity(): Order = Order(
    id = id ?: "",
    passengerName = passengerName ?: "",
    dropOffAddress = dropOffAddress.toEntity(),
    pickUpAddress = pickUpAddress.toEntity(),
)

fun LocationDto.toEntity(): Location = Location(
    lat = lat ?: 0.0,
    lng = lng ?: 0.0,
    addressName = addressName ?: "",
)