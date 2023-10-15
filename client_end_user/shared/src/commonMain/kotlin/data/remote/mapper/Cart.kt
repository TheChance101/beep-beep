package data.remote.mapper

import data.remote.model.CartDto
import domain.entity.Cart
import domain.entity.Price

fun CartDto.toEntity() = Cart(
    meals = meals?.map { it.toEntity(restaurantName, currency) } ?: emptyList(),
    price = Price(totalPrice ?: 0.0, currency ?: ""),
    restaurantId = restaurantId,
    restaurantName = restaurantName,
    restaurantImageUrl = restaurantImage
)

