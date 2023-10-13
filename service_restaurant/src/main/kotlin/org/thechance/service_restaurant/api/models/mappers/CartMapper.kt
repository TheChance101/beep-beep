package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.CartDto
import org.thechance.service_restaurant.domain.entity.Cart


fun Cart.toDto() = CartDto(
    id = id,
    restaurantId = restaurantId,
    restaurantImage= restaurantImage,
    restaurantName = restaurantName,
    meals = meals?.toDto(),
    totalPrice = totalPrice,
    currency = currency
)