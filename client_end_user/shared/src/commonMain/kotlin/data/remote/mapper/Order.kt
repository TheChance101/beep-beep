package data.remote.mapper

import data.remote.model.OrderDto
import domain.entity.Order
import domain.entity.Price

//NEED TO RETURN currency
fun OrderDto.toOrderEntity() = Order(
    id = id,
    userId = userId,
    restaurantId = restaurantId,
    restaurantName = restaurantName,
    restaurantImageUrl = restaurantImageUrl
        ?: "https://takethemameal.com/files_images_v2/stam.jpg",
    meals = meals.toEntity(),
    price = Price(totalPrice ?: 0.0, "$"),
    createdAt = createdAt ?: 0L,
    orderStatus = orderStatus,
    timeToArriveInMints = timeToArriveInMints ?: 0,
)
