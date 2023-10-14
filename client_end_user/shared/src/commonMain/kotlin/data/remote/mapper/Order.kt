package data.remote.mapper

import data.remote.model.OrderDto
import domain.entity.Order
import domain.entity.Price

fun OrderDto.toEntity() = Order(
    id = id ?: "",
    restaurantId = restaurantId ?: "",
    restaurantName = restaurantName ?: "",
    restaurantImageUrl = restaurantImage
        ?: "https://takethemameal.com/files_images_v2/stam.jpg",
    meals = meals?.toEntity(restaurantName, currency = currency) ?: emptyList(),
    price = Price(totalPrice ?: 0.0, currency = currency ?: ""),
    createdAt = createdAt ?: 0L,
    orderStatus = orderStatus,
)

fun List<OrderDto>.toEntity() = map { it.toEntity() }
