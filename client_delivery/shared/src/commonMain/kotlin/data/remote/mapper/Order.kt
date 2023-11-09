package data.remote.mapper

import data.remote.model.OrderDto
import domain.entity.Order
import domain.entity.TripStatus

fun OrderDto.toTripEntity(): Order {
    return Order(
        id = id,
        restaurantName = restaurantName ?: "",
        restaurantImage = restaurantImage ?: "",
        startPoint = startPoint.toEntity(),
        destination = destination.toEntity(),
        startPointAddress = startPointAddress,
        destinationAddress = destinationAddress,
        price = price,
        tripStatus = TripStatus.getOrderStatus(tripStatus)
    )
}